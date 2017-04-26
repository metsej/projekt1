#include<iostream>
#include<stdlib.h>
#include<cstdlib>
#include<stdio.h>
#include<conio.h>
#include<fstream>
#define BOOST_TEST_DYN_LINK
#define BOOST_TEST_MODULE Suites
#include <boost/test/unit_test.hpp>

using namespace std;

void menuglowne();
void admin();
void user();

string adminlogin,adminpass,userlogin,userpass;
string imie,nazwisko;
fstream plik;
string linia;
int nrlinii;

char menu,menuadmin,menuuser;

void menuglowne()
{
   for(;;)
    {
    cout <<"Menu glowne" << endl;
    cout <<"   " << endl;
    cout <<"1.Zaloguj sie jako administrator"<< endl;
    cout <<"2.Zaloguj sie jako uzytkownik" << endl;
    cout <<"3.Zamknij program" << endl;

    menu=getch();

    switch(menu)
    {
    case '1':
        system("cls");
        admin();
    break;

    case '2':
        system("cls");
        user();
    break;


    case '3':
        exit(0);
    break;

    default: cout<<"Wybierz poprawna opcje";
    }
    getchar();
    system("cls");
    }
}
void admin()
{
    cout<<"Wpisz login i haslo zatwierdzajac enterem"<<endl<<endl;
    cout<<"Podaj login : ";
    cin>>adminlogin;
    cout<<"Podaj haslo : ";
    cin>>adminpass;

BOOST_AUTO_TEST_SUITE(AdminSuite)
BOOST_AUTO_TEST_CASE(testloginu)
{
    string adminlogin = "admin";
    BOOST_CHECK(adminlogin="admin");
}
BOOST_AUTO_TEST_CASE(testhasla)
{
    string adminpass = "admin";
    BOOST_CHECK(adminpass="admin");
}
BOOST_AUTO_TEST_SUITE_END()

if((adminlogin=="admin")&&(adminpass=="admin"))
{
    system("cls");

    cout<<"Jestes zalogowany jako administrator"<<endl;
    cout<<endl;
    for(;;)
    {
    cout <<"1.Dodaj pracownika"<< endl;
    cout <<"2.Wyswietl pracownikow" << endl;
    cout <<"3.Wyloguj"<< endl;

    menuadmin=getch();

    switch(menuadmin)
        {
        case '1':

        system("cls");

            cout<<"Imie : ";        cin>>imie;
            cout<<"Nazwisko : ";    cin>>nazwisko;

    plik.open("dane.txt",ios::out | ios::app);
    plik<<imie<<endl;
    plik<<nazwisko<<endl;
    plik.close();
        system("cls");

        break;

    case '2':
cout<<endl;
    break;

    case '3':
        system("cls");
    menuglowne();
    break;

    default: cout<<"Wybierz poprawna opcje";
        }
    }
}
    else
    {
        cout<<endl;
        cout<<"Bledny login lub haslo, wcisnij enter aby wrocic do menu glownego"<<endl;
    }
    cout<<endl;
    getchar();
}
void user()
{
   cout<<"Wpisz login i haslo zatwierdzajac enterem"<<endl<<endl;
    cout<<"Podaj login : ";
    cin>>userlogin;
    cout<<"Podaj haslo : ";
    cin>>userpass;

BOOST_AUTO_TEST_SUITE(UserSuite)
BOOST_AUTO_TEST_CASE(testloginu)
{
    string userlogin = "pracownik";
    BOOST_CHECK(userlogin="pracownik");
}
BOOST_AUTO_TEST_CASE(testhasla)
{
    string userpass = "pracownik";
    BOOST_CHECK(userpass="pracownik");
}
BOOST_AUTO_TEST_SUITE_END()

if((userlogin=="pracownik")&&(userpass=="pracownik"))
{
    system("cls");

    cout<<"Jestes zalogowany jako pracownik"<<endl;

    for(;;)
    {
    cout<<endl;
    cout <<"1.Rozpocznij prace"<< endl;
    cout <<"2.Wyloguj" << endl;

    menuuser=getch();

    switch(menuuser)
        {
    case '1':

    break;

    case '2':
        system("cls");
    menuglowne();
    break;

    default: cout<<"Wybierz poprawna opcje";
        }
    }
}
     else
    {
        cout<<endl;
        cout<<"Bledny login lub haslo, wcisnij enter aby wrocic do menu glownego"<<endl;
    }
    cout<<endl;
    getchar();
}

int main()
{
    menuglowne();

  return 0;
}
