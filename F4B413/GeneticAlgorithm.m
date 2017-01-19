close all
clear variables

% Row : min max bits pos
probleme = [-10 10 6 1; -2.1 3.8 5 7; -50 20 13 12];
% chromosome = [0 1 1 0 0 0 0 1 0 1 1 1 0 1 0 1 1 0 0 0 1 1 0 1];

% probleme = [-10 10 7 1];
% probleme = [-5 5 16 1; -5 5 16 17];

[solution, fit_history] = GeneticAlgorithm(1000, 100, 0.9, 0.2, probleme);

% max(max(fit_history))
% plot(fit_history')

%% Functions
function solution = Decodage (chromosome, probleme)
  count = size(probleme, 1);
  solution = zeros(1, count);
  
  for i = 1:count
      % a: starting pos
      % b: end pos
      a = probleme(i,4);
      b = a + probleme(i,3) - 1;
      
      % Compute quantification step
      min = probleme(i,1);
      max = probleme(i,2);
      delta = (max - min) / (2^probleme(i,3));

      % We flip the bitstring since bin2dec uses the big endian representation
      bitstring = num2str(chromosome(a:b));
      solution(i) = min + bin2dec(flip(bitstring)) * delta;
  end
end

function fitness = Evaluation (solution)
    fitness = 100 + sum(solution);
%   fitness = solution^2;
%   x = solution(1);
%   y = solution(2);
%   fitness = -1 * (20 + exp(1) - 20 * exp(-0.2 * sqrt(0.5*(x^2+y^2))) - exp(0.5*(cos(2*pi*x) + cos(2*pi*y))));
end

%% Generic Genetic Algorithm
% I: iteration count
% P: population size
% Tc: crossover rate
% Tm: mutation rate
function [solution, fit_history] = GeneticAlgorithm(I, P, Tc, Tm, probleme)
  % bs: size in bit of the whole genome
  bs = sum(probleme(:,3));
  % ng: number of genes
  ng = size(probleme, 1);
  
  % Keep fitness history
  fit_history = zeros(P, I);
  
  % Generate initial population
  population = round(rand(P, bs));

  % Main loop
  for i = 1:I
      % Rate individuals
      ratings = zeros(1, P);
      for j = 1:P
          solution = Decodage(population(j,:), probleme);
          ratings(j) = Evaluation(solution);
      end
      
      % We keep the best individual
      [~, ind] = max(ratings);
      best = population(ind,:);
      
      % Selection by rating
      probas = ratings / sum(ratings);
      pool = zeros(1, bs);
      z = 0;
      
      while size(pool, 1) < P
          k = mod(z, P) + 1;
          if rand() < probas(k)
%               disp(['[Mating Pool] Selecting individual ' num2str(k) ' with fitness = ' num2str(ratings(k)) ', probability = ' num2str(probas(k))])
              pool = [pool; population(k,:)];
          end
          z = z + 1;
      end
      
      % Crossover two-by-two
      for j = 0:(P/2)-1
          parent1 = pool(2*j+1,:);
          parent2 = pool(2*j+2,:);
          
          % We cut in the middle
          ci = round(0.5*bs);
          child1 = [parent1(1:ci) parent2(ci+1:bs)];
          child2 = [parent2(1:ci) parent1(ci+1:bs)];
          
          % Insert in population depending on crossover rate
          if rand() < Tc
              population(2*j+1,:) = child1;
              population(2*j+2,:) = child2;
          else
              population(2*j+1,:) = parent1;
              population(2*j+2,:) = parent2;
          end
          
          % Mutation
          for k = 1:2
              for l = 1:ng
                  if rand() < Tm
                      % a: gene start pos, b: gene end pos
                      a = probleme(l,4);
                      % p: bit to mutate pos
                      p = a + round((probleme(l,3) - 1) * rand());
                      population(2*j+k,p) = mod(population(2*j+k,p)+1,2);
                  end
              end
          end
      end
      
      % Re-inject best
      population(ind,:) = best;
      
      % Keep best fitness
      fit_history(:,i) = ratings;
      
      solution = Decodage(best, probleme);
  end
end
