function xs_part=EnKS_lorenz_63(xa_part,xf_part)

%% initialization
[n,N,T]=size(xa_part);
xs_part=zeros(n,N,T);

% Smoother: on parcourt en reverse t=T -> t=1
for k=T:-1:1
    % k
    % xf
    
    % Initialisation (last timestep)
    if k==T
        xs_part(:,:,T)=xa_part(:,:,T);
    else        
        % empirical covariances
        Ef  = xf_part(:,:,k+1) * (eye(N)-1./N.*ones(N));
        Ea  = xa_part(:,:,k) * (eye(N)-1./N.*ones(N));
        Pf  = 1./(N-1) .*Ef *Ef';
        Paf = 1./(N-1) .*Ea *Ef';

        % Kalman smoother
        K = Paf * Pf^(-1);
        
        % xs
        d = xs_part(:,:,k+1) - xf_part(:,:,k+1);
        xs_part(:,:,k) = xa_part(:,:,k) + K*d;
    end % end if
end % fin du for
