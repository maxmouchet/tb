% fonction de répartition de la loi exponentielle
% lamda : parametre lambda
% x : valeur courante
function result = CDFExponential( x, lambda)
    result = 1-exp(-lambda*x);
end