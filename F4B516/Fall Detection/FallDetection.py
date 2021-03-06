from __future__ import division
import cv2
import sys
import numpy as np
from matplotlib import pyplot as plt
from sklearn.mixture import GaussianMixture
from sklearn.mixture import BayesianGaussianMixture

# Parameters
n_components = 1    # Number of gaussian to fit
mhi_duration = 0.99 # Duration of the Motion Interval Images (in seconds)
learning_rate = 0.01

# Video source
path = sys.argv[1]
cap = cv2.VideoCapture(path)

# Background subtractor
fgbg = cv2.BackgroundSubtractorMOG2()
fgbg.setBool('detectShadows', True)
fgbg.setInt('history', 10)
fgbg.setDouble('varThresholdGen', 32)

# Morphologic element to remove background noise
kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE,(5,5))

# Video attributes
nb_frame = int(cap.get(cv2.cv.CV_CAP_PROP_FRAME_COUNT))
width = int(cap.get(cv2.cv.CV_CAP_PROP_FRAME_WIDTH))
height = int(cap.get(cv2.cv.CV_CAP_PROP_FRAME_HEIGHT))
fps = int(cap.get(cv2.cv.CV_CAP_PROP_FPS))

print("Loaded video " + path)
print(str(width) + "x" + str(height) + "@" + str(fps) + "fps")
print(str(nb_frame) + " frames")

# Store last gaussian means to initialize the EM algorithm at the next iteration
# This ensure the same gaussians converges to the same persons in the video
last_means = np.concatenate((np.random.rand(n_components,1)*height, np.random.rand(n_components,1)*width), 1)

# MHI initialization
MHI_DURATION = int(mhi_duration*fps)
raw_mhi = []
for i in range(0, n_components):
    raw_mhi.append(np.zeros((height,width), dtype=np.float32))
mhi = np.zeros((n_components,nb_frame,height,width), dtype=np.uint8)

a_s = np.zeros((n_components,nb_frame))
b_s = np.zeros((n_components,nb_frame))
ab_s = np.zeros((n_components,nb_frame))
theta_s =np.zeros((n_components,nb_frame))

i = 0
while(1):
    ret, frame = cap.read()
    print(i)

    # Exit the loop if the video is finished
    if ret == False:
        break

    # Extract background and create a feature vector from the positions of the white pixels
    fgmask = fgbg.apply(frame,learningRate=learning_rate)
    fgmask = cv2.morphologyEx(fgmask, cv2.MORPH_OPEN, kernel)
    X = np.transpose(np.asarray(np.where(fgmask == 255)))

    # Go to the next frame if there is no foreground
    if X.size == 0:
        continue

    mixture = GaussianMixture(n_components=n_components, means_init=last_means)
    # mixture = BayesianGaussianMixture(n_components=6, weight_concentration_prior=1, mean_precision_prior=1, covariance_prior=1 * np.eye(2))
    mixture.fit(X)
    last_means = np.copy(mixture.means_)

    # Process each gaussian (person) individually
    for k in range(0, n_components):
        mean = mixture.means_[k,:]
        cov = mixture.covariances_[k,:]

        # Center
        pt1 = (int(mean[1]), int(mean[0]))
        v, w = np.linalg.eigh(cov)
        u = w[0] / np.linalg.norm(w[0])
        angle = np.arctan2(u[1], u[0])

        # Convert to degrees
        angle = 180 * angle / np.pi
        v = 2. * np.sqrt(2.) * np.sqrt(v)

        a_s[k,i]=v[0]
        b_s[k,i]=v[1]
        ab_s[k,i]=(v[0]/v[1])
        theta_s[k,i]=(angle)

        # Draw ellipse on video
        cv2.ellipse(frame, pt1, (int(v[1]), int(v[0])), -angle, 0, 360, (64, k*255, 64), 3)

        # Compute silhouette and update mhi
        mask=np.zeros_like(fgmask)
        cv2.ellipse(mask, pt1, (int(v[1]), int(v[0])), -angle, 0, 360, (255, 255, 255), -1)
        silhouette = 255 - np.bitwise_and(fgmask,mask)
        cv2.updateMotionHistory(silhouette, raw_mhi[k], i, MHI_DURATION)

        # Scale and store mhi for processing
        out = cv2.convertScaleAbs(raw_mhi[k], None, 255.0/MHI_DURATION, (MHI_DURATION - i)*255.0/MHI_DURATION)
        mhi[k,i,:,:] = 255 - out

        #cv2.imshow(str(k), mhi[k,i,:,:])
        cv2.imshow('fgmask', fgmask)
        cv2.imshow('frame', frame)

    k = cv2.waitKey(30) & 0xff
    if k == 27:
        break
    i += 1


cap.release()
cv2.destroyAllWindows()

# Fall detection w/ thresholding
std_interval = int(0.5*fps)
std_theta = np.zeros((n_components,nb_frame))
std_ratio = np.zeros((n_components,nb_frame))
Cmotion = np.zeros((n_components,nb_frame))

intervalle = 5
seuil1 = 0.2
seuil2 = 15

for k in range(0,n_components):
    for i in range(0,int(nb_frame)):
        std_theta[k,i] = np.std(theta_s[k,i:i+std_interval])
        std_ratio[k,i] = np.std(ab_s[k,i:i*std_interval])
        Cmotion[k,i] = np.mean(mhi[k,i,:,:])

    for i in range(fps,nb_frame):
        Cmotion_avg = np.mean(Cmotion[k,i-intervalle:i])
        Cmotion_max=np.amax(Cmotion[k,i-intervalle:i]) - Cmotion_avg

        if(Cmotion_max>seuil1):
            std_theta_max=np.amax(std_theta[k,i-intervalle:i])

            if(std_theta_max>seuil2):
                print(str(k) + " is falling on frame " + str(i))
                # print(str(Cmotion_max) + ' ' + str(std_theta_max))


for k in range(0,n_components):
    plt.figure(k)

    plt.subplot(4,1,1)
    plt.plot(Cmotion[k,:])
    plt.ylabel('Cmotion ' + str(k))

    plt.subplot(4,1,2)
    plt.plot(std_theta[k,:])
    plt.ylabel('std angle')

    plt.subplot(4,1,3)
    plt.plot(std_ratio[k,:])
    plt.ylabel('std ratio')
    
    plt.subplot(4,1,4)
    plt.plot(theta_s[k,:])
    plt.ylabel('angle')

plt.show()
