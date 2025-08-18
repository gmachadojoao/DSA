#include <stdio.h>
#include <string.h>

void ehFim(char *str){
    if(str[0] == 'F' && str [1] == 'I' && str [2] =='M'){
        printf("FIM");
    }
}

int contaMaisculua(char *str){
    int contaMais=0;

    for(int i = 0; str[i]!='\0'; i++){
        if(str[i] >='A' && str[i]<='Z'){
            contaMais++;
        }
    }
    return contaMais;
}

int main(){
    char str[100];
    printf("------");
    scanf(" %[^\n]", str);
    contaMaisculua(str);
    ehFim(str);
    printf("%d", contaMaisculua);
    return 0;
}
