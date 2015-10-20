% fonction de répartion de la loi uniformz
% a: valeur min
% b : valeur max
% x : valeur courante
function result = CDFUniform( x, a, b)
    if( x<a)
        result = 0;
    elif( x>b)
        result = 1;
    else
        result = (x-a)/(b-a);
    end
end