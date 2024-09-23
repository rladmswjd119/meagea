## 매개 (Meagea)
개인이 임시 보호를 하고 있는 유기 동물을 등록하고 해당 동물에 대한 홍보글을 작성할 수 있으며, 댓글 형식으로 로그를 작성할 수 있는 API 서버입니다.

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
| 로그 | 추가| POST | /meagea/logs |
| 로그 | 전체 조회 | GET | /meagea/logs |


</br>

## 기술 선택 이유


<details><summary>REST API
</summary>

**Situation**

- 프로젝트 개발 전 기능 정리 겸 URI 통일성 필요

**Task**

- REST API와 RESTful한 코드 작성

**Action**

- REST의 개념과 기본원칙 공부
    - Uniform Interface의 조건 중에서 놓치기 쉬운 ‘메시지는 스스로 설명해야 한다.’ 와 ‘클라이언트가 링크로 상태를 전이할 수 있도록 유도해야 한다.’ 는 조건 위주로 집중했습니다.
- 공부한 개념을 토대로 REST API 작성
    - 요청 메시지 혹은 응답 메시지의 변수명을 설명하는 파일 또한 따로 작성했습니다.
- RESTful한 코드 작성
    - ResponseEntity의 ok() 메서드를 사용해서 객체를 생성하고 body() 메서드에 응답데이터를 저장함으로써 데이터의 상태 정보를 포함시켰습니다.
    - cacheControl() 메서드를 사용해 응답 메시지에 해당 데이터의 캐시에 대한 설정 정보를 포함시켰습니다.ResponseEntity는 해당 데이터를 기본적으로 json으로 판단하기 때문에 자연스럽게 작성 방식 또한 포함시켰습니다.

**Result**

- REST API를 작성하면서 구현할 기능을 점검하고 클라이언트와 서버를 확실하게 분리함으로써 확장과 유지보수가 수월한 코드 작성
</details>


<details><summary>테스트 코드 작성
</summary>

**Situation**

- 웹 화면이 없으면 작성한 코드에 대해 확인하지 못 하는 상황

**Task**

- 테스트 코드를 작성해 웹 화면 없이도 코드 정상적인 실행 확인

**Action**

- Mockito를 이용한 단위 테스트
    - 단위 테스트의 책임에 집중했습니다. 단위 테스트는 지정된 기능이 에러 없이, 예상했던 방향으로 실행되는지에 대해서만 책임을 갖고 있습니다. 따라서 해당 기능을 실행하기 위해 필요한 객체가 제대로 준비되었는지는 확인할 필요가 없었습니다. 그래서 Mock 객체로 대체해 확인하고자 하는 기능에만 집중했습니다.
    - 값을 확인하는 데 필요한 객체를 제외하고 거의 모든 객체를 Mock으로 대체하니 단위 테스트가 짧아져 무엇을 위한 테스트인지 명확해졌습니다.
- TestRestTemplate을 이용한 통합 테스트
    - 실제 데이터를 이용해 기능이 실행되는지 확인해야 했습니다. RestAPI를 바탕으로 코드를 작성했기 때문에 TestRestTemplate을 사용했습니다. 웹 화면을 이용해 테스트했을 때보다 훨씬 빠르고 응답 메시지의 상태 또한 확인할 수 있었습니다.

**Result**

- 테스트 코드 덕분에 웹 화면 없이도 코드가 예상대로 실행되는지 확인할 수 있었습니다.

</details>


<details><summary>Docker
</summary>

**Situation**

- 애플리케이션 실행에 필요한 소프트웨어의 관리가 불편한 상황

**Task**

- 필수적인 소프트웨어를 도커 컨테이너로 관리

**Action**

- 컨테이너를 이용한 가상화
    - 애플리케이션 실행에 필요한 MySQL을 컨테이너로 생성해 가상화하고 버전 업데이트, 삭제, 생성을 수월하게 만들었습니다.
- Docker Volume을 활용한 데이터 백업
    - 컨테이너를 삭제하면 소프트웨어의 데이터도 함께 삭제됩니다. 컨테이너를 삭제하지 않는 방식은 주기적으로 업데이트되는 소프트웨어를 효율적으로 관리할 수 없으므로, Docker Volume을 사용했습니다.

**Result**

- 가상화에 대한 지식을 습득했습니다.
- Docker를 활용해 프로젝트 실행에 필요한 소프트웨어를 쉽게 관리할 수 있게 되었습니다.

</details>



<details><summary>CI를 이용한 통합 자동화
</summary>

**Situation**

- 코드를 수정할 때마다 코드 통합을 위해 빌드, 테스트를 매번 수동으로 실행해서 효율성이 떨어지는 상황

**Task**

- Github Action을 이용해서 통합까지의 일련의 과정 자동화

**Action**

- workflow 작성
- Github action으로 CI를 적용하기 위해 workflow 구조와 문법을 알아야 했습니다. 리서치를 하면 방법이야 많이 나왔지만, workflow에 작성된 코드가 Github action에서 어떻게 적용되는지 알고 싶었습니다.
- Github action 공식 문서와 여러 블로그, 동영상으로 공부를 시작했습니다. 그 결과 Github action은 workflow의 코드를 실행할 VM을 제공하므로 어떤 VM을 사용할 것인지 정해줘야 하는 점, Jobs에 권한을 줘야 프로젝트의 코드를 읽을 수 있다는 점, Job은 병렬로 처리되므로 종속성이 있는 Job은 needs 명령어로 먼저 실행될 Job을 정의해 줘야 한다는 점을 알게 되었습니다. workflow에 쉘 스크립트 명령어도 사용된다는 점 또한 알게 되었습니다.
- 이러한 지식을 바탕으로 workflow를 작성했습니다. VM으로 Ubuntu를 선택하고 Docker 컨테이너로 MySQL 서버를 띄웠습니다. 그리고 순서대로 JDK를 다운로드 받고 gradlew 파일에 실행 권한을 추가했습니다. 마지막으로 gradlew 파일을 빌드했습니다.

**Result**

- CI를 통해 코드의 통합을 자동화함으로써 개발의 효율을 향상할 수 있었습니다.

</details>

</br>

## 트러블 슈팅

<details><summary>MultipartFile 
</summary>

**Situation**

- 홍보글 작성시 다수의 이미지 업로드 필요

**Task**

- 홍보글을 생성할 때 다수의 이미지도 함께 업로드

**Action**

- 사용자가 작성한 홍보글 데이터를 저장하기 위해 DTO 객체 생성하고 스프링에서 제공하는 파일 핸들러인 MultipartFile 사용
    - DTO 객체에 홍보글 이미지를 저장할 List<MultipartFile> 정의했습니다.
- TestRestTemplate을 이용해 컨트롤러에 데이터를 전달하는 테스트 코드를 정의했습니다.
- HttpMessageConversionException 에러가 발생
    - 정확한 판단을 위해 에러의 원인이라고 추측되는 MultipartFile를 제외하고 테스트 진행했습니다. 테스트는 성공했고 이로써 MultipartFile이 컨트롤러로 전달되는 과정에서 에러가 발생했다는 것을 확인했습니다.
    - 에러의 근원지가 AbstractJackson2HttpMessageConverter 클래스라는 것을 확인했습니다. 해당 클래스는 메시지 컨버터 중 하나로 Json 데이터를 자바 객체로 변환시켜 주는 역할을 합니다. Multipartfile 데이터는 Json 형식이 아니기 때문에 자바 객체로 변환시킬 수 없었고 그로인해 에러가 발생한 것입니다.
    - Multipartfile을 분리해서 전달하는 방법도 생각했지만 그렇게 되면 DTO를 사용하는 의미가 퇴색될 것 같아서 다른 방법을 강구했습니다.
- 타입이 다른 데이터를 Map에 담아서 전달할 수 있는 MultiValueMap 사용
    - 단일 키(key)에 다중 값(value) 저장 가능하기 때문에 다수의 MultipartFile을 저장하기 알맞다고 판단했습니다.
    - Multipartfile 생성 후 전달해봤지만 여전히 똑같은 에러가 발생했습니다.
    - 이미지 파일 대용으로 생성한 File 객체를 byte 배열로 변환 후 ByteArrayResource에 저장한 다음 전달했으나 테스트는 실패했습니다.
    - DTO에서 이미지를 저장하는 타입을 List<MultipartFile>에서 List<ByteArrayResource>로 변경했으나 또 테스트는 실패했습니다.
    - 데이터를 응답받는 코드에 디버그를 걸어두고 테스트 실행해봤습니다. MultiValueMap엔 데이터가 잘 저장 되었으나 해당 데이터와 DTO와 매치되지 않아서 컨트롤러에 데이터가 전달되지 않았습니다. 컨트롤러는 전달받은 데이터가 없으니 당연히 빈 객체를 응답했던 것입니다.
    - 이미지 파일 대용으로 생성한 File 객체를 FileSystemResource에 저장 후 전달하니 테스트가 성공했습니다.
</details>

<details><summary>대용량 데이터 처리
</summary>

**Situation**

- 다량의 이미지 업로드 기능을 싱글 스레드 환경에서 실행해 성능이 떨어진 상황

**Task**

- 멀티 스레드 환경에서 비동기와 논블로킹 방식을 이용한 이미지 업로드 기능 구현

**Action**

- 비동기, 동기, 블로킹, 논블로킹에 대해서 공부
    - 호출한 함수를 부모 함수, 호출 당한 함수를 자식 함수라고 가정 했을 때, 동기는 부모 함수가 결과값을 확인하는 방식이고 비동기는 자식 함수가 결과값을 부모 함수에게 알려주는 방식입니다.
    - 블로킹은 부모 함수에게 제어권이 있는 방식이고 논블로킹은 자식 함수에게 제어권이 있는 방식입니다.
    - 비동기, 동기 & 블로킹, 논블로킹으로 만들 수 있는 조합 중 어떤 방식을 선택하느냐에 따라 프로세스가 달라질 수 있으며 이 프로젝트에서는 비동기&논블로킹 방식 사용했습니다.
- @EnableAsync와 @Async 사용
    - mainApplication에 @EnableAsync를 정의하고 비동기 처리를 할 메서드에 @Async를 정의하면 간단하게 비동기를 구현할 수 있었지만 해당 방식은 스레드의 한 종류인 SimpleAsyncTaskExecutor를 사용합니다. 해당 스레드는 Thread Pool을 사용하지 않기 때문에 스레드의 재사용이 불가하며 메서드 실행 시 매번 새로운 스레드를 생성합니다. 이런 방법은 효율적이지 않다고 판단해서 직접 스레드 풀을 생성하는 메서드를 정의했습니다. AsyncConfigurerSupport를 상속받을 수도 있었으나 해당 클래스는 Spring 6.0 이후 사용하는 것을 권장하지 않기 때문에 상속 없이 Thread Pool을 생성했습니다. 이때 초기화를 해주는 initialize() 메서드는 사용하지 않았는데 Spring boot 특성상 Bean으로 등록되면 자동으로 초기화가 되기 때문입니다.
    - @Async을 이용한 비동기 메서드의 반환값은 void 아니면 Future 타입으로 해야하기 때문에 AsyncResult<>() 객체를 사용하려고 했으나 이 또한 Spring 6.0 이후 권장되지 않아서 반환값은 void로 설정했습니다.
    - for문을 포함한 전체 메서드를 비동기로 처리하는 바람에 for문 자체가 한 스레드에서 진행되어 생각했던 대로 비동기 방식이 진행되지 않았습니다. 이후 for문 안에서 이미지 업로드 코드 자체를 비동기 메서드로 정의하니까 업로드 하려는 이미지의 개수만큼 스레드가 생성되어 비동기 방식으로 실행이 되었습니다.
    - @Async 사용시 자가 호출이 불가하기 때문에 비동기 메서드를 정의하기 위한 클래스를 생성했습니다. 해당 클래스에 @Component를 정의해서 Bean으로 등록해주었습니다.
- 불필요한 JPA 제거
    - 비동기 메서드로 이미지 데이터를 DB에 저장한 후 저장된 이미지 데이터를 클라이언트에게 응답 데이터로 전달해줘야 했습니다. 컨트롤러에서 JPA를 사용해 방금 저장한 이미지를 조회하는 방법을 사용했으나 이는 불필요한 JPA를 사용하는 것 같다는 생각이 들었습니다. 효율적인 방법을 고민하다가 비동기 메서드를 실행할 때 저장할 데이터를 CompletableFuture 객체에 담아서 반환하는 방법을 선택했습니다. CompletableFuture를 선택한 이유는 반환된 이후에 값을 조작할 수 있기 때문입니다. 컨트롤러에서 CompletableFuture 객체의 메서드인 supplyAsync를 이용해 반환 값을 가져오고 thenAccept를 이용해서 실질적으로 필요한 이미지 파일을 꺼내고 미리 정의해둔 ArrayList에 더했습니다.
    - 이미지 파일을 업로드할 때 발생할 수 있는 IOException를 비동기 메서드에서 던졌기 때문에 비동기 메서드를 호출한 메서드에 그대로 전달이 되었습니다. 이렇게 되면 supplyAsync의 내부에서 try-catch문을 정의해주어야 하는데 이렇게 되면 가독성이 현저히 떨어질 것 같다고 생각했습니다. 그래서 비동기 메서드에서 try-catch문을 이용해 IOException을 에외 처리를 하지 않아도 되는 RuntimeException으로 매핑해주었습니다.
- CompletableFuture의 활용
    - CompletableFuture의 supplyAsync()가 비동기 메서드이기 때문에 @Async와 동시에 사용할 필요가 없습니다. @Async 대신 CompletableFuture을 사용한 이유는 자가 호출이 가능하고 void나 Future 타입만 반환할 수 있었던 @Async와 다르게 여러 타입으로 반환이 가능하기 때문입니다.
- 멀티 스레드를 활용
    - for문 내부에서 비동기 메서드가 실행되기 때문에 for문이 끝나기 전에는 반환된 CompletableFuture<AnimalFile>의 값을 꺼낼 수 없습니다. 값을 꺼내기 위해서는 get() 혹은 join() 메서드를 사용해야 하는데 해당 메서드들은 블로킹 메서드이므로 사용하면 비동기 메서드를 호출한 스레드가 비동기 메서드에게 제어권을 넘겨주는 것이기 때문에 비동기 메서드가 끝날 때까지 호출한 스레드는 대기 상태가 됩니다. 이는 비동기의 장점을 상실한 것이나 다름 없습니다. 그렇기 때문에 우선 미리 정의해둔 List<CompletableFuture<AnimalFile>>에 비동기 메서드의 반환값을 더해주었습니다.
    - 최종적으로 필요한 데이터의 형태는 List<AnimalFile>이었습니다. List<CompletableFuture<AnimalFile>>을 변환해줘야 했는데 해당 작업을 현재 사용중인 스레드 말고 다른 스레드에 전달해 멀티 스레드 환경을 최대한 이용해주었습니다. 컨트롤러를 통해 비동기 메서드에 전달된 List<CompletableFuture<AnimalFile>>를 우선 배열로 바꿔줍니다. allOf() 메서드를 이용해서 이미지 파일이 전부 반환될 때까지 블로킹하기 위해서 입니다. 그리고 반환값에 추가 작업 후 다시 값을 반환 해주는 thenApply() 내부에서 스트림을 이용해 CompletableFuture<AnimalFile>의 값을 꺼내서 List로 변환해주었습니다.  컨트롤러에는 CompletableFuture<List<AnimalFile>> 형태의 데이터가 전달되었고 get() 메서드를 이용해 값을 꺼내주었습니다.

**Result**

- for문을 비동기 방식으로 실행함으로써 대용량 데이터 처리를 대비했습니다.
- 멀티 스레드와 비동기의 진행 방식에 대한 지식을 습득했습니다.

</details>

