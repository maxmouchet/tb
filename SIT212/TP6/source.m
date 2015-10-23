clear all;
close all;

cmap1 = cbrewer('seq', 'YlGnBu', 100);
cmap2 = cbrewer('seq', 'YlOrBr', 100);

%% Constantes
F = 1000;
lambda = 0.2;
p = 1e-3;

% Calcul de delta_t
t_p = 0.74;
delta_t = 20 * t_p;

% Calcul de delta_f
delta_f = (2 / pi*lambda) * sqrt(-log(p)/2);

%% Question 2.a)
t = [0:1/F:delta_t];

%% Question 2.b)
s_t = (1 / (lambda * sqrt (2 * pi))) * exp(-((t-t_p).^2)./(2*lambda^2));

%% Question 2.c)
% Atténuation et déphasage
alpha = 0.8 * exp(1i*(pi/4));

% Retard (échantillons)
T = 1 / (1/F);

% Signal décalé
s_r = [zeros(1,T) alpha.*s_t];
s_r = s_r(1:length(s_r)-T);

% Signal de sortie
u_t = s_t + s_r;

figure
hold on
set(gcf, 'Color', 'w')

plot(t, s_t,       'color', cmap1((15+10)*3,:))
plot(t, s_r,       'color', cmap1((8+10)*3,:))
plot(t, u_t, '--', 'color', cmap2((10+10)*3,:))

xlabel('Temps (s)')
ylabel('Amplitude')
legend('s(t)', 'g(t)', 'u(t)')
title('Signal émis')

%% Question 3.c)
Npts = length(u_t);
f = -F/2:1/delta_t:F/2;

tf_u = fft(u_t);
tf_u_mod = fftshift(abs(tf_u));
tf_u_arg = fftshift(angle(tf_u));

figure
set(gcf, 'Color', 'w')

subplot(1,2,1)
plot(f, tf_u_mod, 'color', cmap1((15+10)*3,:))

subplot(1,2,2)
plot(f, tf_u_arg, 'color', cmap1((15+10)*3,:))

%% Question 3.d)
%% TODO: f_p ?
milieu = Npts / 2;
fp = f(p / (1/F)) * (1/F);

%% Question 4.a)
W = 1;
beta = 0.8;

h_f = sqrt(W) * (abs(f) <= (1 - beta)  / 2*W) + sqrt((W/2) * (1 + sin(((pi*W)/beta) *((1/(2*W)) - abs(f))))) .* (abs(f) > (1 - beta)  / 2*W) .* (abs(f) <= (1 + beta)  / 2*W);

figure
plot(f, h_f);

%% Question 4.b
%% TODO: f_p ?

