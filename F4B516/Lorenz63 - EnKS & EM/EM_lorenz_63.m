function [var_Q_hat,var_R_hat]=EM_lorenz_63(...
    yo,m,H,var_Q_init,var_R_init,xb,var_B,N,time,nb_iter)

%% initialization
n=length(xb);
[p,T]=size(yo);

for i_iter=1:nb_iter
    i_iter
    
    % EnKF and EnKS
    if i_iter==1
        Q = var_Q_init.*eye(n);
        R = var_R_init.*eye(n);
    else
        [xa_part_EnKF,xf_part_EnKF]=EnKF_lorenz_63(yo,m,H,var_Q_hat(i_iter-1),var_R_hat(i_iter-1),xb,var_B,N,time); 
        xs_part = EnKS_lorenz_63(xa_part_EnKF,xf_part_EnKF);
        
        s = 0;
        for t = 2:T
            a = xs_part(:,:,t) - m(xs_part(:,:,t-1), time(t-1), time(t));
            s = s + a*a';
        end
        Q = s * 1/(N*(T-1));
        
        s = 0;
        for t = 1:T
            a = yo(:,t) - H*xs_part(:,:,t);
            s = s + a*a';
        end
        R = s * 1/(N*T);
    end % end if
    
    % model error covariance
    var_Q_hat(i_iter) = 1/n * trace(Q);
   
    % observation error covariance
    var_R_hat(i_iter) = 1/n * trace(R);
end % end for
