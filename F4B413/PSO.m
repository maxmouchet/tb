close all
clear variables

% Search space [min max]
eps = [-5 5; -5 5];
dim = length(eps);

% Number of particles
N = 50;

% Number of iterations
I = 100;

% Population
P = zeros(N, dim);
for i = 1:dim
    P(:,i) = (eps(i,2) - eps(i,1)) * rand(N,1) - eps(i,2);
end

% Particles speed
Ps = zeros(N, dim);

% Particles best position
Pb = zeros(N, dim);

% Particles best fitness
Pf = zeros(N, 1);

% Global best position
Gb = zeros(1, dim);

% Params
omega = 0.8;
phi1 = 0.5;
phi2 = 1;

figure('Position', [100, 100, 1049, 895])
hold on
colormap winter

X = linspace(-5,5,80);
Y = linspace(-5,5,80);
Z = zeros(2, length(X));
for i = 1:length(X)
    for j = 1:length(Y)
%         Z(i,j) = sin(X(i)) + sin(Y(j));
          Z(i,j) = 20 + exp(1) - 20 * exp(-0.2 * sqrt(0.5*(X(i)^2+Y(j)^2))) - exp(0.5*(cos(2*pi*X(i)) + cos(2*pi*Y(j))));
    end
end
surf(X, Y, Z);
% view(41,83);
view(53,47);

last = plot(P(:,1), P(:,2), 'x');

for i = 1:I
    pause(0.05)
    delete(last)
    last = plot(P(:,1), P(:,2), 'x');
    axis([-5 5 -5 5])
    
    for j = 1:N
       % Fitness evaluation
       fitness = evaluate(P(j,:));
       
       % Update position
       if fitness > Pf(j)
          Pb(j,:) = P(j,:);
       end 
    end
    
    % Update global best
    [~, idx] = max(Pf);
    Gb = Pb(idx,:);
    
    for j = 1:N
        % Variant: Update local best
        dist = zeros(1,N);
        for k = 1:N
            dist(k) = pdist([P(j,:); P(k,:)],'euclidean');
        end
        [~, idx] = sort(dist);
        Np = P(idx(1:4),:);
        [~, idx] = max(Pf(idx(1:4)));
        Lb = Np(idx,:);
        
        % Speed update
        Ps(j,:) = omega*Ps(j,:) + phi1*rand()*(Pb(j,:) - P(j,:)) + phi2*rand()*(Gb - P(j,:));
%         Ps(j,:) = omega*Ps(j,:) + phi1*rand()*(Pb(j,:) - P(j,:)) + phi2*rand()*(Lb - P(j,:));
        P(j,:) = P(j,:) + Ps(j,:);
    end
end

function fitness = evaluate (particle)
%     fitness = -(particle(1)^2 + particle(2)^2);
%     fitness = -(sin(particle(1)) + sin(particle(2)));
      x = particle(1);
      y = particle(2);
      fitness = -1 * (20 + exp(1) - 20 * exp(-0.2 * sqrt(0.5*(x^2+y^2))) - exp(0.5*(cos(2*pi*x) + cos(2*pi*y))));
end