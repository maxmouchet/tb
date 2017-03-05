#include <iostream>
#include <stack>
#include <vector>
#include <tuple>
#include <algorithm>
#include <math.h>
#include <opencv2/opencv.hpp>

using namespace cv;
using namespace std;

// Implementation of Abdel-Dayem et al. 2005
// Carotid Artery Ultrasound Image Segmentation Using Fuzzy Region Growing

Mat image;
Mat image_bin;

// Returns the 8-neighborhood of a point
// Hardcoded for simplicty, but could be generated dynamically
vector<Point> get_neighborhood(Point p) {
    vector<Point> nb;
    nb.push_back(Point(p.x-1, p.y-1));
    nb.push_back(Point(p.x-1, p.y+1));
    nb.push_back(Point(p.x-1, p.y));
    nb.push_back(Point(p.x+1, p.y-1));
    nb.push_back(Point(p.x+1, p.y+1));
    nb.push_back(Point(p.x+1, p.y));
    nb.push_back(Point(p.x, p.y-1));
    nb.push_back(Point(p.x, p.y+1));
    return nb;
}

// Returns the 20 pairs of adjacent points
// Hardcoded for simplicty, but could be generated dynamically
vector<tuple<Point,Point>> get_adjacencies(Point p) {
    vector<tuple<Point,Point>> adj;

    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y-1), Point(p.x-1, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y-1), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y-1), Point(p.x, p.y-1)));

    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y+1), Point(p.x+1, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y+1), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y+1), Point(p.x, p.y+1)));

    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y+1), Point(p.x-1, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y+1), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y+1), Point(p.x, p.y+1)));

    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y-1), Point(p.x+1, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y-1), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y-1), Point(p.x, p.y-1)));

    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y), Point(p.x, p.y+1)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y), Point(p.x, p.y-1)));

    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y), Point(p.x, p.y+1)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y), Point(p.x, p.y-1)));

    adj.push_back(tuple<Point,Point>(Point(p.x, p.y+1), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x, p.y-1), Point(p.x, p.y)));

    return adj;    
}

// Compute \mu_1 and \mu_2
tuple<double, double> compute_mean(Mat& im, Point p) {
    auto adjacencies = get_adjacencies(p);
    
    double sum_1 = 0;
    double sum_2 = 0;

    for (auto adj : adjacencies) {
        sum_1 += im.at<uint8_t>(get<0>(adj)) + im.at<uint8_t>(get<1>(adj));
        sum_2 += abs(im.at<uint8_t>(get<0>(adj)) - im.at<uint8_t>(get<1>(adj)));
    }

    double mean_1 = sum_1 / adjacencies.size();
    double mean_2 = sum_2 / adjacencies.size();

    return tuple<double, double>(mean_1, mean_2);
}

// Compute \sigma_1 and \sigma_2
tuple<double, double> compute_std(Mat& im, Point p) {
    auto adjacencies = get_adjacencies(p);
    
    double sum_1 = 0;
    double sum_2 = 0;
    double sum_1_sq = 0;
    double sum_2_sq = 0;

    for (auto adj : adjacencies) {
        int val_1 = im.at<uint8_t>(get<0>(adj)) + im.at<uint8_t>(get<1>(adj));
        int val_2 = abs(im.at<uint8_t>(get<0>(adj)) - im.at<uint8_t>(get<1>(adj)));
        sum_1 += val_1;
        sum_2 += val_2;
        sum_1_sq += val_1*val_1;
        sum_2_sq += val_2*val_2;
    }
    
    double mean_1 = sum_1 / adjacencies.size();
    double mean_2 = sum_2 / adjacencies.size();
    double mean_1_sq = sum_1_sq / adjacencies.size();
    double mean_2_sq = sum_2_sq / adjacencies.size();

    double std_1 = sqrt(mean_1_sq - mean_1*mean_1);
    double std_2 = sqrt(mean_2_sq - mean_2*mean_2);

    return tuple<double, double>(std_1, std_2);
}

// Compute \Psi(a,b)
double compute_fuzzy_affinity(Mat& im, Point a, Point b, double mu_1, double mu_2, double sigma_1, double sigma_2) {
    double sum_1 = im.at<uint8_t>(a) + im.at<uint8_t>(b);
    double sum_2 = abs(im.at<uint8_t>(a) - im.at<uint8_t>(b));

    double g_1 = exp(((sum_1 - mu_1) * (sum_1 - mu_1)) / (-2 * sigma_1 * sigma_1));
    double g_2 = exp(((sum_2 - mu_2) * (sum_2 - mu_2)) / (-2 * sigma_2 * sigma_2));

    return (g_1 + g_2) / 2;
}

// Compute the fuzzy map, starting in point `seed`
Mat compute_fuzzy_map(Mat& im, Point seed) {
    Mat fuzzy_map = Mat::zeros(im.size(), CV_8U);
    stack<Point> stack;

    stack.push(seed);
    fuzzy_map.at<uint8_t>(seed) = 1;

    // Compute sigma, mu
    tuple<double, double> mean = compute_mean(im, seed);
    tuple<double, double> std = compute_std(im, seed);

    while (!stack.empty()) {
        // Q: temp_pixel
        // CP: current_pixel
        Point current_pixel = stack.top();
        stack.pop();

        for (Point temp_pixel : get_neighborhood(current_pixel)) {
            if (temp_pixel.x < 0 || temp_pixel.y < 0 || temp_pixel.x >= im.size().width || temp_pixel.y >= im.size().height) {
                 continue;
            }
        
            double fm_q = fuzzy_map.at<uint8_t>(temp_pixel);
            double fm_cp = fuzzy_map.at<uint8_t>(current_pixel);
            double psi = compute_fuzzy_affinity(im, current_pixel, temp_pixel, get<0>(mean), get<1>(mean), get<0>(std), get<1>(std));
            if (min(fm_cp, psi) > fm_q) {
                stack.push(temp_pixel);
                fuzzy_map.at<uint8_t>(temp_pixel) = min(fm_cp, psi) * 255;
            }
        }
    }

    return fuzzy_map;
}

// Compute binarization threshold for the fuzzy map
int compute_threshold(Mat& fm) {
    int histogram[256];
    float max_percentage_difference = 0;
    int max_percentage_difference_thr = 0;

    int threshold = 0;
    float area = 0;

    MatConstIterator_<uint8_t> it = fm.begin<uint8_t>(), it_end = fm.end<uint8_t>();
    for(; it != it_end; ++it) {
        uint8_t intensity = *it;
        histogram[intensity]++;
        if (intensity > threshold) {
            threshold = intensity;
        }
    }

    area = histogram[threshold];

    for (int t = threshold; t >= 0; t--) {
        float percentage_difference = histogram[t] / area;
        if (percentage_difference > max_percentage_difference) {
            max_percentage_difference = percentage_difference;
            max_percentage_difference_thr = t;
        }
        area += histogram[t];
    }

    return max_percentage_difference_thr;
}

// Callback for seed selection
void onMouse(int evt, int x, int y, int flags, void* param) {
    if(evt == CV_EVENT_LBUTTONDOWN) {
        // (2.2) Fuzzy region growing
        Mat* image_blurred = (Mat*)param;
        Mat fuzzy_map = compute_fuzzy_map(*image_blurred, Point(x, y));
        // imshow("Fuzzy map", fuzzy_map);

        // (2.3) Threshold selection
        int opt_threshold = compute_threshold(fuzzy_map);
        cout << "Optimal threshold " << opt_threshold << endl;

        // (2.4) Thresholding and boundary extraction
        Mat fuzzy_map_thr;
        threshold(fuzzy_map, fuzzy_map_thr, opt_threshold, 255, THRESH_BINARY);
        // imshow("Binary fuzzy map", fuzzy_map_thr);

        Mat fuzzy_map_closed;
        Mat element = getStructuringElement(MORPH_RECT, Size(5, 5));
        morphologyEx(fuzzy_map_thr, fuzzy_map_closed, MORPH_CLOSE, element);

        Mat fuzzy_map_eroded;
        element = getStructuringElement(MORPH_RECT, Size(3, 3));
        erode(fuzzy_map_closed, fuzzy_map_eroded, element);
        // imshow("Eroded/closed fuzzy map", fuzzy_map_eroded);

        Mat fuzzy_map_boundary = fuzzy_map_closed - fuzzy_map_eroded;
        // imshow("Fuzzy map boundary", fuzzy_map_boundary);

        // Fit ellipse
        vector<vector<Point>> contours;
        Mat tmp = fuzzy_map_boundary.clone();
        findContours(tmp, contours, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_NONE);
        RotatedRect e = fitEllipse(contours[0]);

        // Draw ellipse
        cout << "Ellipse size: " << e.size << endl;
        ellipse(image, e, Scalar(128, 255, 100), 2, 8);
        imshow("Fuzzy map boundary", image);
    }
}

// Callback for threshold selection
void on_trackbar(int value, void* param) {
    Mat image_blurred = *(Mat*)param;
    threshold(image_blurred, image_bin, value, 255, THRESH_BINARY);
    imshow("Pre-processed image", image_bin);
}

int main(int argc, char** argv) {
    if (argc != 2) {
        printf("usage: FuzzyRegionGrowing <Image_Path>\n");
        return -1;
    }

    image = imread(argv[1], CV_LOAD_IMAGE_GRAYSCALE);

    if (!image.data) {
        printf("No image data \n");
        return -1;
    }

    // (2.1) Pre-processing
    // Histogram egalization
    Mat image_eq;
    equalizeHist(image, image_eq);

    // Median filtering to remove speckle noise
    Mat image_blurred;
    medianBlur(image_eq, image_blurred, 3);

    // Show window for seed selection
    // Add slider for binarization
    imshow("Pre-processed image", image_blurred);
    setMouseCallback("Pre-processed image", onMouse, (void*)&image_bin);
    createTrackbar("Binarization threshold", "Pre-processed image", NULL, 255, on_trackbar, (void*)&image_blurred);
    on_trackbar(0, (void*)&image_blurred);

    waitKey(0);

    return 0;
}