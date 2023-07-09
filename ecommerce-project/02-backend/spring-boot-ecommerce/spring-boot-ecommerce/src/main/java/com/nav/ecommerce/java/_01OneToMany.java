package com.nav.ecommerce.java;

import com.nav.ecommerce.entity.Product;

import java.util.Arrays;
import java.util.List;

public class _01OneToMany {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(new Product());
    }
    /*
    Parent
    OneToMany                     ManyToOne
       Customer  -----------------> PhoneNumber
           1                        *
       @OneToMany(mappedBy="customer")                  @ManyToOne
                                                        @JoinColumn(name="customer_id")  //FK
      List<PhoneNumbers>PhoneNumbers phNums;            Customer customer
.
     */

    /*
    N+1 when fetch type eager (ManyToOne or OnetoOne)
    https://vladmihalcea.com/n-plus-1-query-problem/
    List<PostComment> comments = entityManager
.createQuery("""
    select pn
    from PhoneNumbers pn
    """, phoneNumber.class)
.getResultList();
1=> select * frm PhoneNumber
N=> select * from customer whwere customer.customer_id = 1;
    ...                                                = N
Solution:
1. use JOIN FETCH
.createQuery("""
    select pn
    from PhoneNumbers pn join fetch pn.customer c
    """, phoneNumber.class)
    => one join sql
2. FetchType.LAZY
 */
/*
Test1: (withoust cascading)
Customer c1 = new Customer("Navneet);
PhoneNumber p1 = new PhoneNumber(123457,"home")
PhoneNumber p2 = new PhoneNumber(123458,"cell")
c1.setPhoneNumbers(Arrays.asList(p1,p2);
repository.save(customer); // interface CustomerRepository extends CrudRepository<Customer. Long>

!!! Note only Customer is saved
Solution:  @OneToMany(mappedBy="customer", cascade=Cascade.ALL)
customer -> 1, Navneet
phoneNumber -> [id, customer_id, Number, type] (1, Null, 1234567, "home"),(2, Null, 1234568, "cell")
!!! Note customer_id (FK) is null
   => one way was to setCustomer on each PhoneNumber object like p1.setCustomer(customer)
   => better way, do this in Customer class itself
       void addPhoneNumber(PhoneNumber ph){
           ph.setCustomer(customer);
           phNums.add(ph)
           }
==
cust = repository.findOne(4) = only 1 select for customer
so when cust.getPhNumbers() => select phoneNumber happens (must be under @Transactional)
!!! Solution:  @OneToMany(mappedBy="customer", cascade=Cascade.ALL, fetch=fetchType=EAGER)
               => will see 1 join select of cust & phoneNum
@OneToMany(fetch=fetchType=LAZY) is default
OneToMany: LAZY
ManyToOne: EAGER
ManyToMany: LAZY
OneToOne: EAGER
==
l1 cache => session level => comes for free (must be under @Transactional
   => repository.find multiple time but only 1 select
   => evict
      em.unwrap(Session.class);
      Customer c1 = repository.findOne(1);
      repository.findOne(1); => till her 1 select
      session.evict(product);
      repository.findOne(1); => one more select because of evict from cache above, if l2 cache enabled, this extra select wont happen
l2 at SessionFactory level (all sessions share this cache) => configure EhCache
      => add hibernate-ehcache in pom.xml
      => app.properties
          spring.jpa.properties.hibernate.cache.use_second_level_cache=true etc
==============
 */
    /*
 use mydb;

create table customer(
id int PRIMARY KEY AUTO_INCREMENT,
name varchar(20)
);

create table phone_number(
id int PIRMARY KEY AUTO_INCREMENT,
customer_id int,
number varchar(20),
type varchar(20),
 FOREIGN KEY (customer_id)
REFERENCES customer(id)
)

select * from customer

select * from phone_number
     */
}
/*
public class AssociationsApplicationTests {

	@Autowired
	CustomerRepository repository;

	@Autowired
	ProgrammerRepository programmerRepository;

	@Autowired
	LicenseRepository licenseRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testCreateCustomer() {

		Customer customer = new Customer();
		customer.setName("John");

		PhoneNumber ph1 = new PhoneNumber();
		ph1.setNumber("1234567890");
		ph1.setType("cell");

		PhoneNumber ph2 = new PhoneNumber();
		ph2.setNumber("0987654321");
		ph2.setType("home");

		customer.addPhoneNumber(ph1);
		customer.addPhoneNumber(ph2);

		repository.save(customer);
	}

	@Test
	@Transactional
	public void testLoadCustomer() {
		Customer customer = repository.findById(4L).get();
		System.out.println(customer.getName());

		Set<PhoneNumber> numbers = customer.getNumbers();
		numbers.forEach(number -> System.out.println(number.getNumber()));

	}

	@Test
	public void testUpdateCustomer() {
		Customer customer = repository.findById(4L).get();
		customer.setName("John Bush");

		Set<PhoneNumber> numbers = customer.getNumbers();
		numbers.forEach(number -> number.setType("cell"));

		repository.save(customer); => updates both customer and phoneNumber (note it only updates 1 out of 2
		// phoneNumber because one is already cell. Hibernate optimizes updates

	}

	@Test
	public void testDelete() {
		repository.deleteById(4l); => delete phone number and then customer
	}

	@Test
	public void testmtomCreateProgrammer() {
		Programmer programmer = new Programmer();
		programmer.setName("John");
		programmer.setSal(10000);

		HashSet<Project> projects = new HashSet<Project>();
		Project project = new Project();
		project.setName("Hibernate Project");
		projects.add(project);

		programmer.setProjects(projects);

		programmerRepository.save(programmer);
	}

	@Test
	@Transactional
	public void testmtomFindProgrammer() {
		Programmer programmer = programmerRepository.findById(1).get();
		System.out.println(programmer);
		System.out.println(programmer.getProjects());
	}

	@Test
	public void testOneToOneCreateLicense() {
		License license = new License();
		license.setType("CAR");
		license.setValidFrom(new Date());
		license.setValidTo(new Date());

		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Clinton");
		person.setAge(35);

		license.setPerson(person);

		licenseRepository.save(license);
	}

}

 */
