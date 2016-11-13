// Vector of length L
typedef struct Vector { 
    int L;
    double* elems;
} Vector;

// Matrix with M lines and N columns
typedef struct Matrix { 
    int M, N;
    double** elems;
} Matrix;

void debug_matrix(Matrix* matrix, int M, int N);

void debug_vector(Vector* vector, int length);

Vector* allocate_vector(int L);

Matrix* allocate_matrix(int M, int N);

Matrix* reshape(Vector* vector, int M, int N);

Matrix* transpose(Matrix* matrix);

Matrix* multiply(Matrix* matrix1, Matrix* matrix2);

double trace(Matrix* matrix);

double sum(Matrix* matrix);

double tr(Matrix* matrix);

double off(Matrix* matrix);

double vector_mean(Vector* vector);

double vector_std(Vector* vector);

void vector_substract_scalar_inplace(Vector* vector, double scalar);

void matrix_multiply_scalar_inplace(Matrix* matrix, double scalar);