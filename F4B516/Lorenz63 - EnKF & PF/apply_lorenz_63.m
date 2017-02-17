function [dS]=apply_lorenz_63(t,S)
    % apply the Lorenz-63 model with (sigma, beta, rho) parameters
    % declare the physical parameters
    sigma = 10;
    beta = 8/3;
    rho = 28;
    % declare the differential equations (S(i) represents x_i)
    dx_1 = sigma * (S(2) - S(1));
    dx_2 = S(1) * (rho - S(3)) - S(2);
    dx_3 = S(1) * S(2) - beta * S(3);
    dS=[dx_1;dx_2;dx_3];
end % end function