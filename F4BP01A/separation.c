#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "linalg.h"

void separation(double * input_1, double * input_2, double * output_1, double * output_2, int nb, int time_delay) { 

    // Initialize vectors
    Vector* v_s1 = malloc(sizeof(Vector));
    Vector* v_s2 = malloc(sizeof(Vector));
    v_s1->elems = input_1;
    v_s2->elems = input_2;
    v_s1->L = nb;
    v_s2->L = nb;

    // Center mixes
    vector_substract_scalar_inplace(v_s1, vector_mean(v_s1));
    vector_substract_scalar_inplace(v_s2, vector_mean(v_s2));

    // Normalize
    double normalisation = sqrt(2 / (vector_std(v_s1) * vector_std(v_s1) + vector_std(v_s2) * vector_std(v_s2)));
    for (int i = 0; i < v_s1->L; i++) {
        v_s1->elems[i] *= normalisation;
        v_s2->elems[i] *= normalisation;
    }
    
    // Estimate covariance
    int M = time_delay; // Number of time delays for correlation
    int N = v_s1->L / M; // We assume s1 and s2 are the same length

    /// Reshape 1xL to M*(L/M)
    Matrix* m_x1 = reshape(v_s1, M, N);
    Matrix* m_x2 = reshape(v_s2, M, N);
    
    /// Covariance estimation
    Matrix* m_x1t = transpose(m_x1);
    Matrix* m_x2t = transpose(m_x2);

    Matrix* m_r11 = multiply(m_x1, m_x1t);
    matrix_multiply_scalar_inplace(m_r11, (1.0f / N));

    Matrix* m_r12 = multiply(m_x1, m_x2t);
    matrix_multiply_scalar_inplace(m_r12, (1.0f / N));
    
    Matrix* m_r22 = multiply(m_x2, m_x2t);
    matrix_multiply_scalar_inplace(m_r22, (1.0f / N));

    ///Compute coefficients
    double s_f1  = off(m_r11);
    double s_f2  = off(m_r22);
    double s_f12 = off(m_r12);
    double s_t1  = tr(m_r11);
    double s_t2  = tr(m_r22);
    double s_t12 = tr(m_r12);

    double sigma2 = 0;
    double alpha = (2 * s_f12 * s_t12) - (s_f1 * (s_t2 - sigma2) + s_f2 * (s_t1 - sigma2));
    double beta = 2 * (pow(s_t12, 2) - (s_t1 - sigma2) * (s_t2 - sigma2));
    double gamma2 = pow((s_f1*(s_t2 - sigma2) - s_f2*(s_t1 - sigma2)), 2)
                    + 4 * (s_f12*(s_t2 - sigma2) - s_t12*s_f2) * (s_f12*(s_t1 - sigma2) - s_t12*s_f1);

    double s_d1 = alpha - sqrt(gamma2);
    double s_d2 = alpha + sqrt(gamma2);
    
    // Generate matrix
    Matrix* m_est = allocate_matrix(2, 2);

    m_est->elems[0][0] = beta * s_f1  - s_t1  * s_d1;
    m_est->elems[0][1] = beta * s_f12 - s_t12 * s_d2;
    m_est->elems[1][0] = beta * s_f12 - s_t12 * s_d1;
    m_est->elems[1][1] = beta * s_f2  - s_t2  * s_d2;

    // Invert matrix
    double det = (m_est->elems[0][0]* m_est->elems[1][1]) - (m_est->elems[0][1]* m_est->elems[1][0]);

    Matrix* m_est_inv = allocate_matrix(2, 2);
    m_est_inv->elems[0][0] =  m_est->elems[1][1];
    m_est_inv->elems[0][1] = -m_est->elems[0][1];
    m_est_inv->elems[1][0] = -m_est->elems[1][0];
    m_est_inv->elems[1][1] =  m_est->elems[0][0];
    matrix_multiply_scalar_inplace(m_est_inv, 1 / det);

    printf("\nEstimated mixing matrix\n");
    debug_matrix(m_est, 2, 2);
    printf("\nEstimated invert mixing matrix\n");
    debug_matrix(m_est_inv, 2, 2);
    
    det = (m_est_inv->elems[0][0]* m_est_inv->elems[1][1]) - (m_est_inv->elems[0][1]* m_est_inv->elems[1][0]);

    // Sources separation
    for (int i = 0; i < v_s1->L; i++) {
        output_1[i] = (m_est_inv->elems[0][0]*v_s1->elems[i] + m_est_inv->elems[1][0]*v_s2->elems[i]) / det;
        output_2[i] = (m_est_inv->elems[1][0]*v_s1->elems[i] + m_est_inv->elems[1][1]*v_s2->elems[i]) / det;
    }
}
