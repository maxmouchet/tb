% appel [ MuEst, SigmaEst] = estimateurGaussien( data )
% d?termine les estim?es des param?tres\mu et \sigma d'une loi
% gaussienne
% les termes "? compl?ter" sont ? remplacer par la bonne formule matlab.
function [ MuEst, SigmaEst] = estimateurGaussien( data )

    % Estimation de l'esp??rance
    MuEst = mean(data);
    
    % Estimation de la variance
    SigmaEst = var(data);
   
end