% appel Lambda = estimateurExponentielle( data)
% estimateur du param?tre \lambda d'une loi exponentielle
function Lambda = estimateurExponentielle(data)
    N = length(data);
    Lambda = N / sum(data);
end