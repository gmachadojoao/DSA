#include <stdio.h>

int main() {
    char s1[100], s2[100], result[200];
    int i = 0, j = 0, k = 0;

    fgets(s1, sizeof(s1), stdin);
    fgets(s2, sizeof(s2), stdin);

    i = 0;
    while (s1[i] != '\0') {
        if (s1[i] == '\n') {
            s1[i] = '\0';
            break;
        }
        i++;
    }

    j = 0;
    while (s2[j] != '\0') {
        if (s2[j] == '\n') {
            s2[j] = '\0';
            break;
        }
        j++;
    }

    i = 0; j = 0; k = 0;

    while (s1[i] != '\0' && s2[j] != '\0') {
        result[k++] = s1[i++];
        result[k++] = s2[j++];
    }

    while (s1[i] != '\0') {
        result[k++] = s1[i++];
    }

    while (s2[j] != '\0') {
        result[k++] = s2[j++];
    }

    result[k] = '\0';

    printf("%s\n", result);

    return 0;
}
