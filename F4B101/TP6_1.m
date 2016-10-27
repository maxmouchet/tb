clear all;
close all;

% NOTE: Run WavePath to setup WaveLab

SNR = 7;
level = 6;
samples = 2^11;

signal = MakeSignal('Doppler', samples);
[signal_scaled, signal_noised] = NoiseMaker(signal, SNR);

%% Wavelet shrinkage by soft thresholding
% Décomposition
[C, L] = wavedec(signal_noised, level, 'db10'); % L ?

% Soft thresholding
threshold = sqrt(log(samples/2)) + log(1+sqrt(1-(1/(samples^2)))) / sqrt(2*log(samples));
C_threshold = SoftThresh(C, threshold);

% Reconstruction
signal_rec = waverec(C_threshold, L, 'db10');

figure;
subplot(2,2,1);
plot(signal);

subplot(2,2,2);
plot(C);

subplot(2,2,3);
plot(C_threshold);

subplot(2,2,4);
hold on;
plot(signal_scaled);
plot(signal_rec);

%% Discrete stationary wavelet transform
SWC = swt(signal_noised, level, 'db10');
%SWC(1,:) = zeros(1, 2048);
signal_rec_swt = iswt(SWC, 'db10');

figure;
hold on;
plot(signal_scaled);
plot(signal_rec_swt);
