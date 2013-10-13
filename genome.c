#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* Given a DNA codon split into the 3 char parameters, returns the matching Amino acid*/
char codonToSlc(char, char, char);

int main(void) {
	printf("%s", "Poneme el ADN viteh...\nADN: ");

	char adn[10000];
	scanf("%s", adn);
	//printf("ADN: %s\n", adn);

	char aug[3];
	if (adn[0] == 'A' && adn[1] == 'U' && adn[2] == 'G') {
		int i, j = 0, iterations = (strlen(adn) / 3) - 1;
		for (i = 0; i < iterations; i++) {

			char slc = codonToSlc(adn[j + 3], adn[j + 4], adn[j + 5]);
			if (slc != 1) {
				printf("%c", slc);
			} else {
				printf("STOP");
			}
			j += 3;
		}
	} else {
		printf("ADN sequence does not start with AUG");
	}
	return 0;
}

char codonToSlc(char first, char second, char third) {
	switch (first) {
	case 'A':
		switch (second) {
		case 'A':
			switch (third) {
			case 'A':
			case 'G':
				return 'K';
			case 'C':
			case 'T':
				return 'N';
			default:
				return '0';
			}
			break;
		case 'C':
			switch (third) {
			case 'A':
			case 'C':
			case 'T':
			case 'G':
				return 'T';
			default:
				return '0';
			}
			break;
		case 'T':
			switch (third) {
			case 'A':
			case 'C':
			case 'T':
				return 'I';
			case 'G':
				return 'M';
			default:
				return '0';
			}
			break;
		case 'G':
			switch (third) {
			case 'A':
			case 'G':
				return 'R';
			case 'C':
			case 'T':
				return 'S';
			default:
				return '0';
			}
			break;
		}
		break;
	case 'C':
		switch (second) {
		case 'A':
			switch (third) {
			case 'A':
			case 'G':
				return 'Q';
			case 'C':
			case 'T':
				return 'H';
			default:
				return '0';
			}
			break;
		case 'C':
			switch (third) {
			case 'A':
			case 'C':
			case 'T':
			case 'G':
				return 'P';
			default:
				return '0';
			}
			break;
		case 'T':
			switch (third) {
			case 'A':
			case 'C':
			case 'T':
			case 'G':
				return 'L';
			default:
				return '0';
			}
			break;
		case 'G':
			switch (third) {
			case 'A':
			case 'C':
			case 'T':
			case 'G':
				return 'R';
			default:
				return '0';
			}
			break;
		}
		break;
	case 'T':
		switch (second) {
		case 'A':
			switch (third) {
			case 'A':
			case 'G':
				return '1';
			case 'C':
			case 'T':
				return 'Y';
			default:
				return '0';
			}
			break;
		case 'C':
			switch (third) {
			case 'A':
			case 'C':
			case 'T':
			case 'G':
				return 'S';
			default:
				return '0';
			}
			break;
		case 'T':
			switch (third) {
			case 'A':
			case 'G':
				return 'L';
			case 'C':
			case 'T':
				return 'F';
			default:
				return '0';
			}
			break;
		case 'G':
			switch (third) {
			case 'A':
				return '1';
			case 'C':
			case 'T':
				return 'C';
			case 'G':
				return 'W';
			default:
				return '0';
			}
			break;
		}
		break;
	case 'G':
		switch (second) {
		case 'A':
			switch (third) {
			case 'A':
			case 'G':
				return 'E';
			case 'C':
			case 'T':
				return 'D';
			default:
				return '0';
			}
			break;
		case 'C':
			switch (third) {
			case 'A':
			case 'C':
			case 'T':
			case 'G':
				return 'A';
			default:
				return '0';
			}
			break;
		case 'T':
			switch (third) {
			case 'A':
			case 'C':
			case 'T':
			case 'G':
				return 'V';
			default:
				return '0';
			}
			break;
		case 'G':
			switch (third) {
			case 'A':
			case 'C':
			case 'T':
			case 'G':
				return 'G';
			default:
				return '0';
			}
			break;
		}
		break;
	}
	return '0';
}

