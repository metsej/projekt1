#include<iostream>
#include<stdlib.h>
#include<cstdlib>
#include<stdio.h>
#include<conio.h>

using namespace std;

void menuglowne();
void admin();
void user();

char menu,menuadmin,menuuser;

string adminlogin,adminpass,userlogin,userpass;


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
if((adminlogin=="admin")&&(adminpass=="admin"))
{
    system("cls");

    cout<<"Jestes zalogowany jako administrator"<<endl;

    for(;;)
    {
    cout<<endl;
    cout <<"1.Dodaj pracownika"<< endl;
    cout <<"2.Wyswietl pracownikow" << endl;
    cout <<"3.Wyloguj"<< endl;

    menuadmin=getch();

    switch(menuadmin)
        {
    case '1':

    break;

    case '2':

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
