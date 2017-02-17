function xa_part=EnKF_tandeo(yo,m,H,var_Q,var_R,xb,var_B,N,time)

%% initialization
n=length(xb);
[p,T]=size(yo);
xa_part=zeros(n,N,T);

for k=1:T
    k
    % xf
    if k==1
        xf=repmat(xb,1,N)+randn(n,N)*sqrt(var_B);
    else
        % compute the forecast states (use randn)
        eta = sqrt(var_Q) * randn(n,N);
        xf = m(xa_part(:,:,k-1),time(k-1),time(k)) + eta;
    end % fin du if
    Ef=xf*(eye(N)-1./N.*ones(N));
    Pf=1./(N-1).*Ef*Ef';    
    if sum(isfinite(yo(:,k)))>0 % available observations
        % yf
        % compute the forecast observations (use randn)
        eps = sqrt(var_R) * randn(p,N);
        yf = H * xf + eps;
        % Kalman gain and innovation
        K = Pf * H' * (H*Pf*H' + eye(p,p) * var_R)^-1;
        d=repmat(yo(:,k),1,N)-yf;
        % xa
        xa_part(:,:,k) = xf + K*d;
    else % no observations
         xa_part(:,:,k)=xf;
    end % end if
end % fin du for