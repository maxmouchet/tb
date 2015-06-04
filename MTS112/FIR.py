from scipy import signal

import numpy as np
import matplotlib.pyplot as plt

# Synthese d'une filtre FIR
def discrete_lowpass(n, fe, fc, window):
    
    # Determination des coefficients
    b = signal.firwin(n, fc/(fe/2), window=window)
    print(b)

    # Determination de la reponse frequentielle
    w, h = signal.freqz(b, whole=True)

    # Plot
    f, ((ax1, ax2), (ax3, ax4)) = plt.subplots(2, 2, sharex='col', sharey='row')

    ax1.stem(b)
    ax2.plot(w, abs(h))
    ax3.plot(w, 20 * np.log10(abs(h)))
    ax4.plot(w, np.unwrap(np.angle(h)))

    plt.show()
     

    
# Filtre passe-bas d'ordre 7 (Hamming)
discrete_lowpass(8, 16000, 2000, 'hamming')

# Filtre passe-bas d'ordre 19 (Hamming)
discrete_lowpass(20, 16000, 2000, 'hamming')

# Filtre passe-bas d'ordre 19 (Boxcar)
discrete_lowpass(20, 16000, 2000, 'boxcar')


