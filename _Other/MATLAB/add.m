function [ M ] = add(A, B)
    if isequal(size(A), size(B))
         M = A+B;
    else
        error('A and B have different sizes')
    end
end