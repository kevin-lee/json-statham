Changelog
=========

0.2.54-SNAPSHOT
---------------
* Methods to write JSON which return a CharAndStringWritable object which contains the given Writer / OutputStream are added. The CharAndStringWritable object is neither flushed nor closed so the caller should do it (probably after using it more). ([212f094...](https://github.com/Kevin-Lee/json-statham/commit/212f094adfe22debc818bb590585308c71b010a1))

0.2.53-SNAPSHOT
---------------
* pom.xml: ASM 3.3.1 -> 5.0.3 : This version supports Java 8. ([f1bb577...](https://github.com/Kevin-Lee/json-statham/commit/f1bb577626dc826cde2e1594b493b7a69d0d12d0))

0.2.48-SNAPSHOT
---------------
* [97e2048...](https://github.com/Kevin-Lee/json-statham/commit/97e2048089b02f0bd02f74e080dbbfdeb7bc94a4)
* [#95](http://projects.elixirian.org/json-statham/ticket/95): Support for Writer and OutputStream to write JSON ([73d6692...](https://github.com/Kevin-Lee/json-statham/commit/73d6692fb10ca0f831720bf2a6cdee58e937d6af))
* [ed8e32c...](https://github.com/Kevin-Lee/json-statham/commit/ed8e32c6da21ad7019e29c3964aba235fdcf99bb)
* [8b0d433...](https://github.com/Kevin-Lee/json-statham/commit/8b0d433c8b72228e2a54d1a0d05f9bada470d434)
* [#94](http://projects.elixirian.org/json-statham/ticket/94): Support for InputStream and Reader to read JSON ([33f4843...](https://github.com/Kevin-Lee/json-statham/commit/33f48438d0ea17acb852be85160eae9628e272ed))
* [05ead79...](https://github.com/Kevin-Lee/json-statham/commit/05ead79168b13c8916fa45b54e291947a35c10fa)
* [3fed3cc...](https://github.com/Kevin-Lee/json-statham/commit/3fed3cc3095e4098f086e45169132f6b31a935a0)
* [12dd4a1...](https://github.com/Kevin-Lee/json-statham/commit/12dd4a1755d63405cda6e3fea67fc7fefb62ced5)


0.2.43-SNAPSHOT
---------------
* Creating JSON object using non-matching field and constructor param ([#91](http://projects.elixirian.org/json-statham/ticket/91))

0.2.42-SNAPSHOT
---------------
* JSON -> Java doesn't work properly for some JSON string ([#90](http://projects.elixirian.org/json-statham/ticket/90))

0.2.36-SNAPSHOT
---------------
* Pair shouldn't be treated as a name value pair of JSON ([#89](http://projects.elixirian.org/json-statham/ticket/89))

0.2.35-SNAPSHOT
---------------

**Rest will be added...**