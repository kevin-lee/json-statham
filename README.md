JSON Statham - Java JSON Mapper
===============================

[![Build Status](https://travis-ci.org/Kevin-Lee/json-statham.svg)](https://travis-ci.org/Kevin-Lee/json-statham)

~~[Project Homepage](http://projects.elixirian.org/json-statham)~~

JSON Statham (pronounced [**dƷéisn steɪθəm**] the same as the name of the actor, Jason Statham. If you are still not sure, watch [Jason Statham Crank Interview](http://www.youtube.com/watch?v=TDh0PZCO2CU)) is an **"open source"** **Java JSON Mapping** library.  It is completely **free** and will continute to be a free open source library ([Read more about JSON Statham](#StartingPoints)). JSON Statham provides an easy way of converting Java object into JSON and JSON into Java object.
The only requirement to use JSON Stathem is using a few annotations in the JSON Statham library on the target Java objects.
Just this simple task will result in JSON.

e.g.)

 * Target object:

```java
import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;

@Json
public class Pojo
{
  @JsonField
  private final long id;

  @JsonField
  private final String name;

  @JsonField
  private final String address;

  public Pojo(long id, String name, String address)
  {
    this.id = id;
    this.name = name;
    this.address = address;
  }
}
```

 * **Java to JSON**

```java


final Pojo pojo = new Pojo(5, "Kevin Lee", "123 ABC Street");
final String result = jsonStatham.convertIntoJson(pojo);
System.out.println(result);

```
 * Result:
```
{
  "id":5,
  "name":"Kevin Lee",
  "address":"123 ABC Street"
}
```

 * **JSON to Java**

```java
final String json = "{\"id\":5,\"name\":\"Kevin Lee\",\"address\":\"123 ABC Street\"}";
final Pojo pojo = jsonStatham.convertFromJson(Pojo.class, json);

```



 * Target objects:

```java
import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;

@Json
public class Pojo
{
  @JsonField
  private final Long id;

  @JsonField(name = "fullName");
  private final String name;

  @JsonField
  private final Address address;

  public Pojo(Long id, String name, Address address)
  {
    this.id = id;
    this.name = name;
    this.address = address;
  }
}
```
```java
import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;

@Json
public class Address
{
  @JsonField
  private final String street;

  @JsonField
  private final String suburb;

  @JsonField
  private final String city;

  @JsonField
  private final String state;

  @JsonField
  private final String postcode;

  public Address(String street, String suburb, String city, String state, String postcode)
  {
    this.street = street;
    this.suburb = suburb;
    this.city = city;
    this.state = state;
    this.postcode = postcode;
  }
}
```

 * **Java to JSON**

```java
final Pojo pojo = new Pojo(5L, "Kevin Lee", new Address("123 ABC Street", "", "Sydney", "NSW", "2000"));
final String result = jsonStatham.convertIntoJson(pojo);
System.out.println(result);

```

 * Result:

```json
{
  "id":5,
  "fullName":"Kevin Lee",
  "address":
  {
    "street":"123 ABC Street",
    "suburb":"",
    "city":"Sydney",
    "state":"NSW",
    "postcode":"2000"
  }
}
```

 * **JSON to Java**

```java
final String json = "{\"id\":5,\"fullName\":\"Kevin Lee\",\"address\":{\"street\":\"123 ABC Street\",\"suburb\":\"\",\"city\":\"Sydney\",\"state\":\"NSW\",\"postcode\":\"2000\"}}";
final Pojo pojo = jsonStatham.convertFromJson(Pojo.class, json);

```



 * Converting **Java array into JSON**

```java
System.out.println(jsonStatham.convertIntoJson(new String[] { "aaa", "bbb", "ccc" }));
```

```
[
  "aaa",
  "bbb",
  "ccc"
]
```

 * Converting **JSON array into Java array**

```java
final String[] result = jsonStatham.convertFromJson(String[].class, "[\"aaa\",\"bbb\",\"ccc\"]");
for (final String word : result)
{
  System.out.println(word);
}

```

```
aaa
bbb
ccc
```



* Converting **Java collection into JSON array**

```java
System.out.println(jsonStatham.convertIntoJson(Arrays.asList("aaa", "bbb", "ccc")));

```
```
["aaa","bbb","ccc"]
```

* Converting **JSON array into Java collection**

```java
@SuppressWarnings("unchecked")
final Collection<String> resultCollection1 =
  jsonStatham.convertFromJson(Collection.class, "[\"aaa\",\"bbb\",\"ccc\"]");
System.out.println(resultCollection1);

```
```
[aaa, bbb, ccc]
```

* OR

```java
final Collection<String> resultCollection2 = jsonStatham.convertFromJson(new TypeHolder<Collection<String>>(){},
      "[\"aaa\",\"bbb\",\"ccc\"]");
System.out.println(resultCollection2);

```
```
[aaa, bbb, ccc]
```



* Converting **Java Map into JSON**

```java


final Map<String, String> surnameToGivenNameMap = new HashMap<String, String>();
surnameToGivenNameMap.put("Lee", "Kevin");
surnameToGivenNameMap.put("Kent", "Clark");
surnameToGivenNameMap.put("Wayne", "Bruce");

System.out.println(jsonStatham.convertIntoJson(surnameToGivenNameMap));

```
```
{
  "Kent":"Clark",
  "Lee":"Kevin",
  "Wayne":"Bruce"
}
```


* Converting **JSON into Java Map**

```java
@SuppressWarnings("unchecked")
final Map<String, String> surnameToGivenNameMapFromJson1 =
  jsonStatham.convertFromJson(Map.class,
                                    "{\"Kent\":\"Clark\",\"Lee\":\"Kevin\",\"Wayne\":\"Bruce\"}");

System.out.println(surnameToGivenNameMapFromJson1);

```
```
{Kent=Clark, Lee=Kevin, Wayne=Bruce}
```

* OR

```java
final Map<String, String> surnameToGivenNameMapFromJson2 =
  jsonStatham.convertFromJson(new TypeHolder<Map<String, String>>(){}, 
      "{\"Kent\":\"Clark\",\"Lee\":\"Kevin\",\"Wayne\":\"Bruce\"}");

System.out.println(surnameToGivenNameMapFromJson2);

```
```
{Kent=Clark, Lee=Kevin, Wayne=Bruce}
```


* Converting **Java Map having nested Lists into JSON**

```java
final Map<String, List<List<Integer>>> map = new LinkedHashMap<String, List<List<Integer>>>();

map.put("Kevin",
    Arrays.asList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
      Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19, 20),
      Arrays.asList(21, 22, 23, 24, 25, 26, 27, 28, 29, 30)));

map.put("Lee",
    Arrays.asList(Arrays.asList(100, 200, 300, 400, 500, 600, 700, 800, 900, 1000),
      Arrays.asList(1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 11000)));

System.out.println(jsonStatham.convertIntoJson(map));
```
```
{
  "Kevin":
  [
    [1,2,3,4,5,6,7,8,9,10],[11,12,13,14,15,16,17,18,19,20],[21,22,23,24,25,26,27,28,29,30]
  ],
  "Lee":
  [
    [100,200,300,400,500,600,700,800,900,1000],[1100,1200,1300,1400,1500,1600,1700,1800,1900,11000]
  ]
}
```

* [[=#Converting-JSON-into-Java-Map-having-nested-Lists|Converting **JSON into Java Map having nested Lists**]]

```java
final Map<String, List<List<Integer>>> nameToListOfListOfNumber =
  jsonStatham.convertFromJson(
      new TypeHolder<Map<String, List<List<Integer>>>>(){},
      "{\"Kevin\":[[1,2,3,4,5,6,7,8,9,10],[11,12,13,14,15,16,17,18,19,20],[21,22,23,24,25,26,27,28,29,30]],\"Lee\":[[100,200,300,400,500,600,700,800,900,1000],[1100,1200,1300,1400,1500,1600,1700,1800,1900,11000]]}");

System.out.println(nameToListOfListOfNumber);
```
```
{Lee=[[100, 200, 300, 400, 500, 600, 700, 800, 900, 1000], [1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 11000]], Kevin=[[1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [11, 12, 13, 14, 15, 16, 17, 18, 19, 20], [21, 22, 23, 24, 25, 26, 27, 28, 29, 30]]}
```

[Getting started with JSON Statham](https://github.com/Kevin-Lee/json-statham/wiki/Getting-Started) is a good place to start.

Enjoy!

[Kevin](http://lckymn.com)

##Starting Points

 * [Changelog](/Kevin-Lee/json-statham/blob/master/Changelog.md)
 * [Getting started with JSON Statham](https://github.com/Kevin-Lee/json-statham/wiki/Getting-Started)
 * [User Manual](https://github.com/Kevin-Lee/json-statham/wiki)
 * [Get JSON Statham using Maven](https://github.com/Kevin-Lee/json-statham/wiki/Getting-Started#maven-users) (recommended)
