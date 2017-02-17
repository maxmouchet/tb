function xa_part=PF_lorenz_63(yo,m,H,var_Q,var_R,xb,var_B,N,time)

%% initialization
n=length(xb);
[p,T]=size(yo);
xa_part=zeros(n,N,T);

for k=1:T
    k
    % xf
    if k==1
        xf=repmat(xb,1,N) + randn(n,N) * sqrt(var_B);
    else
        % compute the forecast states (use randn)
        eta = sqrt(var_Q) * randn(n,N);
        xf = m(xa_part(:,:,k-1),time(k-1),time(k)) + eta;
    end % fin du if
    if sum(isfinite(yo(:,k)))>0 % available observations
        % weights
        for j=1:N
            % compute weights
            weights(j) = gaussian_pdf(H*xf(:,j), yo(:,k), eye(p,p) * var_R);
        end % end for
        weights=weights/sum(weights);
        % resampling
        indic=resample_multinomial(weights);
        % xa
        xa_part(:,:,k) = xf(:,indic);
    else % no observations
        xa_part(:,:,k) = xf(:,indic);
    end % end if
end % fin du for
