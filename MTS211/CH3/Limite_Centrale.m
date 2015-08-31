% Bornes de repre??sentation (histogramme et pdf)
a=0;
b=6;

%
% Simulation de la loi binomiale comme la somme de 'n' tirages de Bernoulli
%

% Parame??tres de la loi binomiale
n=6;
p = 0.5;

% Nombre de re??alisations de la v.a. binomiale
n_tir = 10000;

% (Q3) Ge??ne??ration des tirages de Bernoulli (n_tir x n)
ber = rand(n_tir,n) < p;

% (Q3) Calcul des 'n_tir' re??alisations de la v.a. binomiale
s = sum(ber,2)';

% (Q4) Histogramme de ces re??alisations
pas = 1;
x2 = a:b;
histo = histogram (s, a, pas, b);

% (Q5) Remise a?? l'e??chelle
histo = histo / (n_tir*pas)

%
% (Q6) Loi gaussienne the??orique selon le the??ore??me de la limite centrale
% (s'il s'applique)
%
mu = n*p;
sigma = sqrt(n*p*(1-p));
x1 = a-.5:0.01:b+.5;
pdf_th = 1/(sqrt(2*pi*sigma^2)) * exp(-(x1-mu).^2/(2*sigma^2));

% (Q7) Visualisation
figure (50);
[AX,H1,H2] = plotyy (x2, histo , x1 , pdf_th , 'bar' , 'stairs');
set(get(AX(1),'Ylabel'),'String','Pr(B=x)','Color','red')
set(get(AX(2),'Ylabel'),'String','f_N(x)')
set(H2,'LineWidth', 2 , 'Color' , 'red');
set(H1,'FaceColor', 'blue');
xlabel ('x')
grid on;
title ('Illustration du the??ore??me limite centrale');
legend ('Loi Binomiale (6,0.5)', ...
        'Loi Normale (3,1.5)', ...
        'Location', 'NorthWest');