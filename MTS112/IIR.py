from scipy import signal

import numpy as np
import matplotlib.pyplot as plt

# Synthese d'un filtre IIR
fe = 16000

# Frequence de coupure dans la bande passante
fp = 2000
wp = fp/(fe/2)

# Frequence de coupure dans la bande attenuee
fa = 4000
ws = fa/(fe/2)

# Taux d'ondulation dans la bande passante (dB)
delta1 = 3

# Taux d'ondulation dans la bande attenuee (dB)
delta2 = 30

# Determination de l'ordre et de la frequence de coupure
N, Wn = signal.buttord(wp, ws, delta1, delta2)

# Determination des coefficients
a, b = signal.butter(N, Wn)

# Determination de la reponse frequentielle
w, h = signal.freqz(b, a, whole=True)

# Plot
f, (ax1, ax2) = plt.subplots(2)

ax1.plot(w, 20 * np.log10(abs(h)))
ax2.plot(w, np.unwrap(np.angle(h)))

plt.show()

# Decomposition en cellules du 1er et du 2eme ordre
Z = signal.zpk2sos(np.roots(a), np.roots(b), a[0])
print(Z)
