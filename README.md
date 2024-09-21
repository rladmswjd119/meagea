## 매개 (Meagea)
개인이 유기 동물을 홍보할 수 있는 사이트

>기간 : 2024.02 ~ 2024.04

</br>

## 목차
> 1. [프로젝트 환경](#프로젝트-환경)
> 2. [ERD](#ERD)
> 3. [API 명세서](#API-명세서)
> 4. [기술 선택 이유](#기술-선택-이유)
> 5. [트러블 슈팅](#트러블-슈팅)

</br>

## 프로젝트 환경
| Stack                                                                                                        | Version           |
|:------------------------------------------------------------------------------------------------------------:|:-----------------:|
| ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) | Spring Boot 3.3.x |
| ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)    | Gradle 8.8       |
| ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)    | JDK 17           |
| ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)       | MySQL latest        |

</br>

## ERD
![image](https://github.com/user-attachments/assets/a471977b-638c-4015-895e-d4f66e314624)


</br>

## API 명세서
| 대분류 | 기능 | Http Method | API Path |
| --- | --- | --- | --- |
| 동물 | 추가 | POST | /meagea/animals |
| 동물 | 조회 | GET | /meagea/animals/{no} |
| 동물 | 전체 삭제| DELETE | /meagea/animals |
| 동물 | 전체 조회 | GET | /meagea/animals |
| 홍보글 | 추가 | POST | /meagea/promotions | 
| 홍보글 | 조회 | GET | /meagea/promotions/{no} |
| 홍보글 | 수정| PATCH | /meagea/promotions |
| 홍보글 | 전체 조회 | GET | /meagea/promotions |
| 홍보글 | 삭제| DELETE | API /meagea/promotions |
| 로그 | 조회| GET | /meagea/logs |
| 로그 | 전체 조회 | GET | /meagea/logs |


</br>

## 기술 선택 이유

</br>

## 트러블 슈팅
