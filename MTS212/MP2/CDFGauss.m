% fonction de répartition de la loi gaussiennne
% x : valeur courante
% mu : moyenne
% sigma : ecart type
function result = CDFGauss( x, mu, sigma)
    result = ( 1+erf( ( x-mu )/( sigma*sqrt(2) ) ) )/2;
end