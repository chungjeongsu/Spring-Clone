package spring;

public class SpringApplication {

    /**
     * 기본 순서
     *
     * 1. BeanDefinition 등록 단계
     *  1-1. springCloneApplicationClazz를 구성 소스로 등록
     *       - AnnotatedGenericBeanDefinition 형태로 BeanDefinitionRegistry(BeanFactory)에 추가
     *
     *  1-2. ConfigurationClassPostProcessor 실행
     *       - Registry 안의 BeanDefinition들 중
     *         @Configuration / @Component / @SpringBootApplication / @ComponentScan / @Import / @Bean
     *         등을 가진 것들을 "설정 클래스 후보(Configuration class candidate)"로 마킹
     *
     *  1-3. while(새로운 설정 클래스 후보가 더 이상 나오지 않을 때까지) {
     *      1-3-1. 각 설정 클래스(ConfigurationClass)에 대해 parse()
     *             - 파라미터로 BeanDefinitionRegistry + 환경(Environment) 등
     *
     *      1-3-2. @ComponentScan 발견 시(재귀)
     *             - basePackage 기준으로 classpath 스캔
     *             - @Component / @Configuration / @Repository / @Service / @Controller 등 찾음
     *
     *      1-3-3. 스캔/파싱 중에
     *             - 새로운 @Configuration 클래스 발견 → 설정 클래스 그래프에 추가(다음 루프 대상)
     *             - @Bean 메서드 → 메서드 기반 BeanDefinition 생성
     *
     *      1-3-4. 위에서 발견된 것들을 BeanDefinitionRegistry에 BeanDefinition으로 추가
     *             - "설정 클래스"도 BeanDefinition
     *             - 일반 @Component/@Bean 도 BeanDefinition
     *             - 계속 스캔 루프를 돌며, registry에 추가
     *  }
     *
     *  1-4. 이 시점에 BeanDefinitionRegistry에는
     *       - @Configuration / @Component / @Bean / @Import / @ComponentScan 결과 등
     *         애플리케이션에 필요한 거의 모든 BeanDefinition이 등록된 상태
     *
     * 2. Bean 생성 단계 (인스턴스 생성)
     *  2-1. ApplicationContext.refresh() 마지막에
     *       - beanFactory.preInstantiateSingletons() 호출
     *
     *   2-1-1. non-lazy singleton BeanDefinition 목록을 루프 돌면서 getBean(beanName)
     *
     *   2-1-2. getBean() 내부에서 createBean() 파이프라인 실행
     *          - InstantiationStrategy로 인스턴스 생성(리플렉션/팩토리 메서드/CGLIB 등)
     *          - 의존 주입(생성자/필드/세터)
     *          - 초기화 콜백(@PostConstruct 등)
     *          - BeanPostProcessor 실행 (여기서 AOP 프록시도 생성)
     *
     *   2-1-3. scope별 저장
     *          - singleton  → DefaultSingletonBeanRegistry의 singletonObjects(Map)에 캐싱
     *          - prototype  → 캐싱 없음. getBean()마다 새로 생성
     *          - request/session → Scope 구현체(RequestScope, SessionScope)가
     *                              HttpServletRequest / HttpSession에 저장
     *                              (ThreadLocal은 "현재 요청/세션 객체"를 가져오기 위한 브리지일 뿐,
     *                               Bean 인스턴스 자체가 ThreadLocal에 저장되는 건 아님)
     *
     *  2-2. 웹 애플리케이션(Spring Boot)인 경우
     *       - ServletWebServerApplicationContext가 WebServer(Tomcat 등)를 띄우고
     *       - DispatcherServlet 빈을 생성
     *       - 이 ApplicationContext를 DispatcherServlet에 주입해서
     *         "요청 → DispatcherServlet → HandlerMapping/Adapter → Bean들" 구조로 연결
     */
    public static void run(Class<?> springCloneApplicationClazz) {

    }
}
