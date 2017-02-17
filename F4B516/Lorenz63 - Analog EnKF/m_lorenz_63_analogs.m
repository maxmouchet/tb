function x_end=m_lorenz_63_analogs(x,analogs,successors)
    % apply the analog method corresponding to the Lorenz-63 model for the N members of x
    % x(1:3,i): member i of the state variables (x, y, z) of the Lorenz-63 system
    % analogs: the analogs of the catalog (time t)
    % successors: the successors of the catalog (time t+1)
    N=size(x,2); % number of members N
    
    % Number of neighbors
    K = 25;
    
    % loop on members
    for i_N=1:N
        
        % find the indices of the k-nearest neighbors (using knnsearch)  
        index_knn = knnsearch(x(1:3, i_N)', analogs, K);

        % empirical mean and covariance (using mean and cov)
        xf_mean = mean(successors(index_knn, :));
        cov_xf = cov(successors(index_knn, :));

        % A) Random sampling from the multivariate Gaussian pdf (using mvg)
        % x_end(:,i_N) = mvg(xf_mean', cov_xf, 1);
        
        % B) Linear regression
        coeffs = analogs(index_knn,:) \ successors(index_knn,:);
        x_end(:,i_N) = x(:,i_N)'*coeffs;
        
        % C) Using SVM regression
        % mdl1 = fitrsvm(analogs(index_knn,:), successors(index_knn,:));
        % mdl2 = fitrsvm(analogs(index_knn,:), successors(index_knn,2));
        % mdl3 = fitrsvm(analogs(index_knn,:), successors(index_knn,3));
        % x_end(1,i_N) = predict(mdl1, x(:,i_N)');
        % x_end(2,i_N) = predict(mdl2, x(:,i_N)');
        % x_end(3,i_N) = predict(mdl3, x(:,i_N)');
    end % end for

end % end function
