clear all;
close all;

%% Image denoising
lena = double(imread('lena.png'));
level = 1;
[C, S] = wavedec2(lena, level, 'db1');
lena_rec = waverec2(C, S, 'db1');

figure;
imshow(uint8(lena));

figure;
imshow(uint8(lena_rec));