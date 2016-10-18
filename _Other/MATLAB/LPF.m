function LPF(fc)
  f = 1:10^6;
  H = 1./(1+1j*(f/fc));
  G = 10*log10(abs(H).^2);
  
  figure;
  semilogx(f,G);
  xlabel('Frequency');
  ylabel('Gain (dB)');
  title('Bode plot');
end