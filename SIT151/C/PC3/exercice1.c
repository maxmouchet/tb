#include <stdio.h>

typedef struct {
  char last_name[20];
  char first_name[15];
  int  age;
} Person;


int ask() {
  printf("1. Add user\n");
  printf("2. Quit\n");
  return getchar() - 48;
}

Person createPerson() {
  Person p;

  printf("Last name: ");
  scanf("%s", p.last_name);

  printf("First name: ");
  scanf("%s", p.first_name);

  printf("Age: ");
  scanf("%d", &p.age);

  return p;
}

int main() {
  FILE *f;

  if ((f = fopen("users.txt", "a")) != NULL) {

    int choice;
    while ((choice = ask()) != 2) {
      if (choice == 1) {
	Person p = createPerson();
	fprintf(f, "%s_%s_%d\n", p.last_name, p.first_name, p.age);
      }
    }

    fclose(f);
  } else {
    printf("Error while opening users.txt\n");
  }

  return 0;
}
