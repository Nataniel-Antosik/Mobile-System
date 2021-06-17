import matplotlib.pyplot as plt
import math
import scipy.constants
import numpy as np


def zadanie1(GT, GR, lam, d):
    wyjscie = []
    for i in range(len(d)):
        wyjscie.append(GT * GR * (lam / (4 * math.pi * d[i])) ** 2)
    return wyjscie

def zadanie2(dane, c):
    wyjscie = np.zeros(dane.shape[0])
    for i in range(dane.shape[0]):
        wyjscie[i] = (dane[i] / c)
    return wyjscie

def zadanie3(GT, GR, f, c, d, h1, h2):
    wyjscie = np.zeros(d.shape[0])
    lam = c / f
    d1 = np.zeros(d.shape[0])
    d2 = np.zeros(d.shape[0])
    phi1 = np.zeros(d.shape[0])
    phi2 = np.zeros(d.shape[0])
    for i in range(d.shape[0]):
        d1[i] = np.sqrt((h1 - h2)**2 + d[i]**2)
        d2[i] = np.sqrt((h1 + h2)**2 + d[i]**2)

    for i in range(d1.shape[0]):
        phi1[i] = (-1) * 2 * np.pi * f * (d1[i] / c)
        phi2[i] = (-1) * 2 * np.pi * f * (d2[i] / c)

    for i in range(d.shape[0]):
        wyjscie[i] = (GT * GR * ((lam / (4 * np.pi)) ** 2)) * abs(((1 / d1[i]) * np.exp(1j * phi1[i])) - ((1 / d2[i]) * np.exp(1j * phi2[i]))) ** 2

    return wyjscie

def zad1():
    d = list(range(1, 101))
    d1 = list(range(1, 10001))
    c = 300000000
    f = 900000000
    lista = zadanie1(1.6, 1.6, c / f, d)
    lista2 = zadanie1(1.6, 1.6, c / f, d1)

    decybele1 = []
    decybele2 = []

    for i in range(len(lista)):
        decybele1.append(10 * math.log10(lista[i]))

    for i in range(len(lista2)):
        decybele2.append(10 * math.log10(lista2[i]))

    plt.figure()
    plt.title("Zad1 1-100m")
    plt.plot(decybele1)
    plt.xlabel('Dystans[m]')
    plt.ylabel('Spadek mocy[Db]')
    plt.savefig('Zad1_1-100m.png', dpi=600)
    plt.show()

    plt.figure()
    plt.title("Zad1 1-10km")
    plt.plot(decybele2)
    plt.xlabel('Dystans[m]')
    plt.ylabel('Spadek mocy[Db]')
    plt.savefig('Zad1_1-10km.png', dpi=600)
    plt.show()

def zad2():
    zadanie21 = np.zeros(100)
    zadanie22 = np.zeros(10000)

    for i in range(zadanie21.shape[0]):
        zadanie21[i] = i + 1

    for i in range(zadanie22.shape[0]):
        zadanie22[i] = i + 1

    zadanie21 = zadanie2(zadanie21, 3000000)
    zadanie22 = zadanie2(zadanie22, 3000000)

    plt.figure()
    plt.title("zad2 1-100m")
    plt.plot(zadanie21)
    plt.xlabel('Dystans[m]')
    plt.ylabel('Opóźnienie')
    plt.savefig('zad2_1-100m.png', dpi=600)
    plt.show()

    plt.figure()
    plt.title("zad2 1-10km")
    plt.plot(zadanie22)
    plt.xlabel('Dystans')
    plt.ylabel('Opóźnienie')
    plt.savefig('zad2_1-10km.png', dpi=600)
    plt.show()

def zad3():

    listaDo3b = []

    licznik = 1
    for i in range(10001):
        if i % 10 == 0 and i != 0:
            listaDo3b.append(i)
            licznik += 1

    print(listaDo3b)
    print("ilosc: ", len(listaDo3b))

    zadanie3a = np.arange(1.0, 100.1, 0.1)
    zadanie3b = np.arange(1.0, 10000.1, 0.1)

    c = scipy.constants.c
    f = 900000000
    Gt = 1.6
    Gr = 1.6
    h1 = 30
    h2 = 3
    #zadanie3a od 1 do 100 krok 1
    #zadanie3b od 10 do 10000 krok 10
    zadanie3a = zadanie3(Gt, Gr, f, c, zadanie3a, h1, h2)
    zadanie3b = zadanie3(Gt, Gr, f, c, zadanie3b, h1, h2)

    decybele1 = []
    decybele2 = []

    for i in range(zadanie3a.shape[0]):
        decybele1.append(10 * math.log10(zadanie3a[i]))

    for i in range(zadanie3b.shape[0]):
        decybele2.append(10 * math.log10(zadanie3b[i]))

    plt.figure()
    plt.title("zad3 1-100m")
    plt.plot(decybele1)
    plt.xlabel('Dystans[m]')
    plt.ylabel('Spadek mocy[Db]')
    plt.savefig('zad3_1-100m.png', dpi=600)
    plt.show()

    plt.figure()
    plt.title("zad3 1-10km")
    plt.plot(decybele2)
    plt.xscale("log")
    plt.xlabel('Dystans')
    plt.ylabel('Spadek mocy[Db]')
    plt.savefig('zad3_1-10km.png', dpi=600)
    plt.show()

if __name__ == "__main__":
    #zad1()
    #zad2()
    zad3()
    print("COMPLEX: ", complex(0, 1))
    print(np.imag(1j))
