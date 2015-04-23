function [ M ] = mult(A, B)
    if isequal(size(A,2), size(B,1))
         M = A*B;
    else
        error('Number of columns in A does not match number of lines in B')
    end
end