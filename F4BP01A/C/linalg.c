#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <assert.h>
#include <math.h>
#include "linalg.h"

#define DEBUG 1
#define debug_print(fmt, ...) \
    do { if (DEBUG) fprintf(stderr, "%s:%d:%s(): " fmt, __FILE__, \
                            __LINE__, __func__, __VA_ARGS__); } while (0)

Vector* allocate_vector(int L) {
    Vector* vector = malloc(sizeof(Vector));
    vector->L = L;
    vector->elems = malloc(L * sizeof(double));
    return vector;
}

Matrix* allocate_matrix(int M, int N) {
    Matrix* matrix = malloc(sizeof(Matrix));
    matrix->M = M;
    matrix->N = N;
    matrix->elems = malloc(M * sizeof(double*));
    for (int i = 0; i < M; i++) {
        matrix->elems[i] = malloc(N * sizeof(double));
    }
    return matrix;
}

void debug_matrix(Matrix* matrix, int M, int N) {
    printf("\n");
    for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
            printf("%f ", matrix->elems[i][j]);
        }
        printf("\n");
    }
}

void debug_vector(Vector* vector, int length) {
    printf("\n");
    for (int i = 0; i < length; i++) {
        printf("%f ", vector->elems[i]);
    }
    printf("\n");
}

double vector_mean(Vector* vector) {
    double sum = 0;
    for (int i = 0; i < vector->L; i++) {
        sum += vector->elems[i];
    }
    return (sum / vector->L);
}

double vector_std(Vector* vector) {
	double s = 0;
	double m = vector_mean(vector);
	for (int i=0; i < vector->L; i++) {
        s += pow((vector->elems[i] - m), 2);
    }
	return sqrt(s / vector->L);
}

double trace(Matrix* matrix) {
    assert(matrix->M == matrix->N);
    double sum = 0;
    for (int i = 0; i < matrix->M; i++) {
        sum += matrix->elems[i][i];
    }
    return sum;
}

double sum(Matrix* matrix) {
    double sum = 0;
    for (int i = 0; i < matrix->M; i++) {
        for (int j = 0; j < matrix->N; j++) {
            sum += matrix->elems[i][j];
        }
    }
    return sum;
}

double tr(Matrix* matrix) {
    return (1.0f / matrix->M) * trace(matrix);
}

double off(Matrix* matrix) {
    return (1.0f / (matrix->M * (matrix->M - 1))) * (sum(matrix) - trace(matrix));
}

// Reshape a 1xL vector to a Mx(L/M) matrix
// M : Number of lines
Matrix* reshape(Vector* vector, int M, int N) {
    if ((M * N) != vector->L) {
        debug_print("Size mismatch: %d x %d != %d\n", M, N, vector->L);
    }

    Matrix* matrix = allocate_matrix(M, N);
    int pos = 0;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            matrix->elems[j][i] = vector->elems[pos];
            pos++;
        }
    }

    return matrix;
}

Matrix* transpose(Matrix* matrix) {
    Matrix* output = allocate_matrix(matrix->N, matrix->M);
    for (int i = 0; i < matrix->M; i++) {
        for (int j = 0; j < matrix->N; j++) {
            output->elems[j][i] = matrix->elems[i][j];
        }
    }
    return output;
}

Matrix* multiply(Matrix* matrix1, Matrix* matrix2) {
    assert(matrix1->N == matrix2->M);
    Matrix* output = allocate_matrix(matrix1->M, matrix2->N);
    for (int i = 0; i < matrix1->M; i++) {
        for (int j = 0; j < matrix2->N; j++) {
            for (int k = 0; k < matrix1->N; k++) {
                output->elems[i][j] += (matrix1->elems[i][k] * matrix2->elems[k][j]);
            }
        }
    }
    return output;
}

void vector_substract_scalar_inplace(Vector* vector, double scalar) {
    for (int i = 0; i < vector->L; i++) { vector->elems[i] -= scalar; }
}

void matrix_multiply_scalar_inplace(Matrix* matrix, double scalar) {
    for (int i = 0; i < matrix->M; i++) {
        for (int j = 0; j < matrix->N; j++) {
            matrix->elems[i][j] *= scalar;
        }
    }
}