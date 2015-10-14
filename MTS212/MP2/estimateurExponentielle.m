% appel Lambda = estimateurExponentielle( data)
% estimateur du paramètre \lambda d'une loi exponentielle
function Lambda = estimateurExponentielle( data)
    N = size(data,1);
    
    Lambda = N / sum(data);
end