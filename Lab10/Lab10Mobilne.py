import matplotlib.pyplot as plt
import math
import scipy.constants
import numpy as np

#Tłumienie w swobodnej przestrzeni
def fun1(f, d):
    #d - odległość w m
    #f - częstotliwość MHz czyli 2400 MHz
    L = -27.55 + 20 * np.log10(f) + 20 * np.log10(d)
    return L

#ITU-R P.1238
def fun2(f, d, N, Lf):
    L = 20 * np.log(f) + N * np.log(d) + Lf - 28
    return L

#Model One-Slope
def fun3(f, gamma, d):
    L0 = fun1(f, d)
    L = L0 + 10 * gamma * np.log(d)
    return L

#Model Motleya-Keenana
def fun4(LFS, nW, LW, nf, Lf2):
    L = LFS + nW * LW + nf * Lf2
    return L

#Model Multi-Wall
def fun5(f, gamma, d, kwi, Lwi, kfj, Lfj):
    L0 = fun1(f, d)
    sumaKWI = 0
    sumaKFJ = 0
    for i in range(kwi):
        sumaKWI += 1
    for i in range(kfj):
        sumaKFJ += 1
    L = L0 + 10 * gamma * np.log(d) + sumaKWI * Lwi + sumaKFJ * Lfj
    return L

#Bilans energetyczny
def fun6(Pn, Gn, Go, L, A):
    Po = 20 - L
    return Po

def rysowanie(daneZew, dane1, dane2, dane3, dane4, dane5, tytul):
    a11 = fun6(20, 20, 20, dane1[0], 20)
    a12 = fun6(20, 20, 20, dane1[1], 20)
    a13 = fun6(20, 20, 20, dane1[2], 20)
    a1 = [a11, a12, a13]
    a21 = fun6(20, 20, 20, dane2[0], 20)
    a22 = fun6(20, 20, 20, dane2[1], 20)
    a23 = fun6(20, 20, 20, dane2[2], 20)
    a2 = [a21, a22, a23]
    a31 = fun6(20, 20, 20, dane3[0], 20)
    a32 = fun6(20, 20, 20, dane3[1], 20)
    a33 = fun6(20, 20, 20, dane3[2], 20)
    a3 = [a31, a32, a33]
    a41 = fun6(20, 20, 20, dane4[0], 20)
    a42 = fun6(20, 20, 20, dane4[1], 20)
    a43 = fun6(20, 20, 20, dane4[2], 20)
    a4 = [a41, a42, a43]
    a51 = fun6(20, 20, 20, dane5[0], 20)
    a52 = fun6(20, 20, 20, dane5[1], 20)
    a53 = fun6(20, 20, 20, dane5[2], 20)
    a5 = [a51, a52, a53]
    x = [1, 2, 3]
    plt.title(tytul)
    plt.plot(x, daneZew, 'ko', label='Dane WiFi Analyzer')
    plt.plot(x, a1, 'bo', label="Swoboda przestrzeń")
    plt.plot(x, a2, 'co', label="ITU-R P.1238")
    plt.plot(x, a3, 'mo', label="Model One-Slope")
    plt.plot(x, a4, 'yo', label="Model Motleya-Keenana")
    plt.plot(x, a5, 'ro', label="Model Multi-Wall")
    plt.xlabel('Numer danego pomiaru')
    plt.ylabel('Bilans Energetyczny [dBm]')
    plt.xlim(0., 7.)
    plt.legend()
    plt.savefig(tytul + ".png")
    plt.show()

def testowanie():
    # swobodna przestrzeni gamma = 2, wewnątrz budynku gamma = <3,5, 6>
    # d = 5, f = 2400
    a = fun1(2400, 5)
    # d = 5, f = 2400, N = 28  Lf = 4 * 2
    b = fun2(2400, 5, 28, 4 * 2)
    # f = 2400, gamma = 2, d 5
    c = fun3(2400, 2, 5)
    # LFS = 7, nW = 2, LW = 2, nf = 1, Lf2 = 11
    d = fun4(fun1(2400, 5), 2, 2, 1, 11)
    # f = 2400, gamma = 2, d = 5, kwi = 2, Lwi = 7, kfj = 1, Lfj = 11
    e = fun5(2400, 2, 5, 2, 7, 1, 11)
    print("Tłumienie w swobodnej przestrzeni:   ", a, " dB")
    print("ITU-R P.1238:                        ", b, " dB")
    print("Model One-Slope:                     ", c, " dB")
    print("Model Motleya-Keenana:               ", d, " dB")
    print("Model Multi-Wall:                    ", e, " dB")

    print("================")
    print("Tłumienie w swobodnej przestrzeni")
    f = fun6(20, 20, 20, a, 20)
    print("Bilans energetyczny: ", f, " dBm")
    print("================")
    print("ITU-R P.1238")
    f = fun6(20, 20, 20, b, 20)
    print("Bilans energetyczny: ", f, " dBm")
    print("================")
    print("Model One-Slope")
    f = fun6(20, 20, 20, c, 20)
    print("Bilans energetyczny: ", f, " dBm")
    print("================")
    print("Model Motleya-Keenana")
    f = fun6(20, 20, 20, d, 20)
    print("Bilans energetyczny: ", f, " dBm")
    print("================")
    print("Model Multi-Wall")
    f = fun6(20, 20, 20, e, 20)
    print("Bilans energetyczny: ", f, " dBm")
    print("================")



testowanie()
#wifi analizer
#5 metod
#================Dane na zewnątrz================#
#1 ściana
#3m, 10m, 11m
daneZew=[-49, -60, -61]
metry = [3, 10, 11]
dane1 = [fun1(2400, 3), fun1(2400, 10), fun1(2400, 11)]
dane2 = [fun2(2400, 3, 28, 4 * 0), fun2(2400, 10, 28, 4 * 0), fun2(2400, 11, 28, 4 * 0)]
dane3 = [fun3(2400, 2, 3), fun3(2400, 2, 10), fun3(2400, 2, 11)]
dane4 = [fun4(fun1(2400, 3), 1, 9, 0, 11), fun4(fun1(2400, 10), 1, 9, 0, 11), fun4(fun1(2400, 11), 1, 9, 0, 11)]
dane5 = [fun5(2400, 2, 3, 1, 9, 0, 11), fun5(2400, 2, 10, 1, 9, 0, 11), fun5(2400, 2, 11, 1, 9, 0, 11)]
rysowanie([-49, -60, -61], dane1, dane2, dane3, dane4, dane5, "Dane na zewnątrz")
#================Pomiędzy piętrami================#
#Strop, Strop + ściana, Strop
#4m, 22m, 54m
danePie = [-53, -67, -75]
metry = [4, 22, 54]
dane1 = [fun1(2400, metry[0]), fun1(2400, metry[1]), fun1(2400, metry[2])]
dane2 = [fun2(2400, metry[0], 28, 4 * 1), fun2(2400, metry[1], 28, 4 * 1), fun2(2400, metry[2], 28, 4 * 1)]
dane3 = [fun3(2400, 3.5, metry[0]), fun3(2400,  3.5, metry[1]), fun3(2400, 3.5, metry[2])]
dane4 = [fun4(fun1(2400, metry[0]), 0, 9, 1, 11), fun4(fun1(2400, metry[1]), 1, 9, 1, 11), fun4(fun1(2400, metry[2]), 0, 9, 1, 11)]
dane5 = [fun5(2400, 3.5, metry[0], 0, 7, 1, 11), fun5(2400, 3.5, metry[1], 1, 7, 1, 11), fun5(2400, 3.5, metry[2], 0, 7, 1, 11)]
rysowanie(danePie, dane1, dane2, dane3, dane4, dane5, "Pomiędzy piętrami")
#================Pomiędzy ścianami================#
#1 ściana
#39m, 4, 15m
daneSci = [-72, -53, -60]
metry = [39, 4, 15]
dane1 = [fun1(2400, metry[0]), fun1(2400, metry[1]), fun1(2400, metry[2])]
dane2 = [fun2(2400, metry[0], 28, 4 * 0), fun2(2400, metry[1], 28, 4 * 0), fun2(2400, metry[2], 28, 4 * 0)]
dane3 = [fun3(2400, 3.5, metry[0]), fun3(2400,  3.5, metry[1]), fun3(2400, 3.5, metry[2])]
dane4 = [fun4(fun1(2400, metry[0]), 1, 9, 0, 11), fun4(fun1(2400, metry[1]), 1, 9, 1, 11), fun4(fun1(2400, metry[2]), 1, 9, 0, 11)]
dane5 = [fun5(2400, 3.5, metry[0], 1, 7, 0, 11), fun5(2400, 3.5, metry[1], 1, 7, 0, 11), fun5(2400, 3.5, metry[2], 1, 7, 0, 11)]
rysowanie(daneSci, dane1, dane2, dane3, dane4, dane5, "Pomiędzy ścianami")
#================Wewnątrz budynku================#
#Brak ściany
#1m, 3m, 3m
daneBud = [-37, -49, -50]
metry = [39, 4, 15]
dane1 = [fun1(2400, metry[0]), fun1(2400, metry[1]), fun1(2400, metry[2])]
dane2 = [fun2(2400, metry[0], 28, 4 * 0), fun2(2400, metry[1], 28, 4 * 0), fun2(2400, metry[2], 28, 4 * 0)]
dane3 = [fun3(2400, 3.5, metry[0]), fun3(2400,  3.5, metry[1]), fun3(2400, 3.5, metry[2])]
dane4 = [fun4(fun1(2400, metry[0]), 0, 9, 0, 11), fun4(fun1(2400, metry[1]), 0, 9, 1, 11), fun4(fun1(2400, metry[2]), 0, 9, 0, 11)]
dane5 = [fun5(2400, 3.5, metry[0], 0, 7, 0, 11), fun5(2400, 3.5, metry[1], 0, 7, 0, 11), fun5(2400, 3.5, metry[2], 0, 7, 0, 11)]
rysowanie(daneBud, dane1, dane2, dane3, dane4, dane5, "Wewnątrz budynku (brak ścian)")
