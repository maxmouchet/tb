#include <iostream>
#include <stack>
#include <vector>
#include <tuple>
#include <algorithm>
#include <math.h>
#include <opencv2/opencv.hpp>
// #include <ope

using namespace cv;
using namespace std;

// Implementation of Abdel-Dayem et al. 2005
// Carotid Artery Ultrasound Image Segmentation Using Fuzzy Region Growing

vector<Point> get_neighborhood(Point p) {
    vector<Point> nb;
    nb.push_back(Point(p.x-1, p.y-1));
    nb.push_back(Point(p.x-1, p.y+1));
    nb.push_back(Point(p.x-1, p.y));
    nb.push_back(Point(p.x+1, p.y-1));
    nb.push_back(Point(p.x+1, p.y+1));
    nb.push_back(Point(p.x+1, p.y));
    nb.push_back(Point(p.x, p.y-1));
    nb.push_back(Point(p.x, p.y+1));
    return nb;
}

vector<tuple<Point,Point>> get_adjacencies(Point p) {
    vector<tuple<Point,Point>> adj;

    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y-1), Point(p.x-1, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y-1), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y-1), Point(p.x, p.y-1)));

    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y+1), Point(p.x+1, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y+1), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y+1), Point(p.x, p.y+1)));

    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y+1), Point(p.x-1, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y+1), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y+1), Point(p.x, p.y+1)));

    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y-1), Point(p.x+1, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y-1), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y-1), Point(p.x, p.y-1)));

    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y), Point(p.x, p.y+1)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x-1, p.y), Point(p.x, p.y-1)));

    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y), Point(p.x, p.y+1)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x+1, p.y), Point(p.x, p.y-1)));

    adj.push_back(tuple<Point,Point>(Point(p.x, p.y+1), Point(p.x, p.y)));
    adj.push_back(tuple<Point,Point>(Point(p.x, p.y-1), Point(p.x, p.y)));

    return adj;    
}

tuple<double, double> compute_mean(Mat& im, Point p) {
    auto adjacencies = get_adjacencies(p);
    
    double sum_1 = 0;
    double sum_2 = 0;

    for (auto adj : adjacencies) {
        sum_1 += im.at<uint8_t>(get<0>(adj)) + im.at<uint8_t>(get<1>(adj));
        sum_2 += abs(im.at<uint8_t>(get<0>(adj)) - im.at<uint8_t>(get<1>(adj)));
    }

    double mean_1 = sum_1 / adjacencies.size();
    double mean_2 = sum_2 / adjacencies.size();

    return tuple<double, double>(mean_1, mean_2);
}

tuple<double, double> compute_std(Mat& im, Point p) {
    auto adjacencies = get_adjacencies(p);
    
    double sum_1 = 0;
    double sum_2 = 0;
    double sum_1_sq = 0;
    double sum_2_sq = 0;

    for (auto adj : adjacencies) {
        int val_1 = im.at<uint8_t>(get<0>(adj)) + im.at<uint8_t>(get<1>(adj));
        int val_2 = abs(im.at<uint8_t>(get<0>(adj)) - im.at<uint8_t>(get<1>(adj)));
        sum_1 += val_1;
        sum_2 += val_2;
        sum_1_sq += val_1*val_1;
        sum_2_sq += val_2*val_2;
    }
    
    double mean_1 = sum_1 / adjacencies.size();
    double mean_2 = sum_2 / adjacencies.size();
    double mean_1_sq = sum_1_sq / adjacencies.size();
    double mean_2_sq = sum_2_sq / adjacencies.size();

    double std_1 = sqrt(mean_1_sq - mean_1*mean_1);
    double std_2 = sqrt(mean_2_sq - mean_2*mean_2);

    return tuple<double, double>(std_1, std_2);
}

double compute_fuzzy_affinity(Mat& im, Point a, Point b, double mu_1, double mu_2, double sigma_1, double sigma_2) {
    double sum_1 = im.at<uint8_t>(a) + im.at<uint8_t>(b);
    double sum_2 = abs(im.at<uint8_t>(a) - im.at<uint8_t>(b));

    double g_1 = exp(((sum_1 - mu_1) * (sum_1 - mu_1)) / (-2 * sigma_1 * sigma_1));
    double g_2 = exp(((sum_2 - mu_2) * (sum_2 - mu_2)) / (-2 * sigma_2 * sigma_2));

    return (g_1 + g_2) / 2;
}

Mat compute_fuzzy_map(Mat& im, Point seed) {
    Mat fuzzy_map = Mat::zeros(im.size(), CV_8U);
    stack<Point> stack;

    stack.push(seed);
    fuzzy_map.at<uint8_t>(seed) = 1;

    // Compute sigma, mu
    tuple<double, double> mean = compute_mean(im, seed);
    tuple<double, double> std = compute_std(im, seed);
    cout << "mu_1 = " << get<0>(mean) << ", mu_2 = " << get<1>(mean) << endl;
    cout << "sigma_1 = " << get<0>(std) << ", sigma_2 = " << get<1>(std) << endl;

    while (!stack.empty()) {
        // Q: temp_pixel
        // CP: current_pixel
        Point current_pixel = stack.top();
        stack.pop();

        for (Point temp_pixel : get_neighborhood(current_pixel)) {
            if (temp_pixel.x < 0 || temp_pixel.y < 0 || temp_pixel.x >= im.size().width || temp_pixel.y >= im.size().height) {
                 continue;
            }
        
            double fm_q = fuzzy_map.at<uint8_t>(temp_pixel);
            double fm_cp = fuzzy_map.at<uint8_t>(current_pixel);
            double psi = compute_fuzzy_affinity(im, current_pixel, temp_pixel, get<0>(mean), get<1>(mean), get<0>(std), get<1>(std));
            if (min(fm_cp, psi) > fm_q) {
                stack.push(temp_pixel);
                fuzzy_map.at<uint8_t>(temp_pixel) = min(fm_cp, psi) * 255;
            }
        }
    }

    return fuzzy_map;
}

int main(int argc, char** argv) {
    
    // if (argc != 2) {
    //     printf("usage: FuzzyRegionGrowing <Image_Path>\n");
    //     return -1;
    // }

    Mat image;
    // image = imread(argv[1], 1);
    image = imread("../Images_2016 (fist set)/P1_Image_originale.png", CV_LOAD_IMAGE_GRAYSCALE);

    // (2.1) Pre-processing
    // Histogram egalization

    // Median filtering to remove speckle noise


    // (2.2) Fuzzy region growing
    Mat fuzzy_map = compute_fuzzy_map(image, Point(589,334));
    // cout << fuzzy_map << endl;


    if (!image.data) {
        printf("No image data \n");
        return -1;
    }
    


    namedWindow("Display Image", WINDOW_AUTOSIZE );
    imshow("Display Image", fuzzy_map);

    waitKey(0);

    return 0;
}