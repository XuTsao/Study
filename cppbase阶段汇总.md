# 第一章 C++与C

本章主要讲解C++相较于C一些独有的比较重要的知识点。

> C++源文件后缀名：`.cc`或`.cpp`
> 头文件后缀名：`.hh`或`.hpp`
>
> 安装g++命令：`sudo apt install g++`
>
> 编译命令：`g++ filename.cc [-o name]`

首先从C++的 hello,world 程序入手，来认识一下C++语言。

```C++
#include <iostream>
using namespace std;

int main(int argc, char * argv[]) {
 	cout << "hello,world" << endl;
	return 0;
}
```

- iostream是C++的头文件，为什么没有后缀？—— 模板阶段再作讲解

- using namespace std是什么含义？—— 命名空间的使用

- cout << "hello,world" << endl; 实现了输出hello,world的功能，如何理解这行代码？—— cout的使用

  << 输出流运算符	>>输出流运算符



## 命名空间

> 《c++ primer plus》第9章 内存模型和名称空间

###  为什么要使用命名空间

一个大型的工程往往是由若干个人独立完成的，不同的人分别完成不同的部分，最后再组合成一个完整的程序。由于各个头文件是由不同的人设计的，有可能在不同的头文件中用了**相同的名字**来命名所定义的类或函数，这样在程序中就会出现名字冲突。不仅如此，有可能我们自己定义的名字会与**C++库中的名字**发生冲突。

**名字冲突就是在同一个作用域中有两个或多个同名的实体。**

为了解决命名冲突 ，C++中引入了命名空间，所谓==**命名空间**就是一个可以由用户自己定义的作用域，在不同的作用域中可以定义相同名字的变量，互不干扰，系统能够区分它们==。

c语言中避免名字冲突，只能进行起名约定

```c
int hw_cpp_tom_num = 100;
int wd_cpp_bob_num = 200;
```



###  什么是命名空间

命名空间又称为名字空间，是程序员命名的内存区域，程序员根据需要指定一些有名字的空间域，把一些**==全局实体==**分别存放到各个命名空间中，从而与其他全局实体分隔开。通俗的说，每个名字空间都是一个名字空间域，存放在名字空间域中的全局实体只在本空间域内有效。名字空间对全局实体加以域的限制，从而合理的解决命名冲突。

C++中定义命名空间的基本格式如下：

```C++
namespace wd
{
    int val1 = 0;
    char val2;
}// end of namespace wd
```

在声明一个命名空间时，大括号内不仅可以存放变量，还可以存放以下类型：

==变量、常量、函数、结构体、引用、类、对象、模板、命名空间==等，它们都称为**==实体==**。

（1）请尝试定义命名空间，并在命名空间中定义实体。

（2）命名空间中的实体如何使用呢？

```cpp
namespace wd
{
    int num = 100;
    void func(){
        vout << "func" << endl;
    }
}// end of namespace wd

namespace hw
{
    int num = 100;
    void func(){
        vout << "func" << endl;
    }
}// end of namespace wd

void(test0)
{
	// cout << num << endl;
	// func();
	cout << wd:num << endl;
	wd::func();
}
```



### 命名空间的三种使用方式

命名空间一共有三种使用方式，分别是using编译指令、作用域限定符、using声明机制。

1. ==**作用域限定符`::`**==

   每次要使用某个命名空间中的实体时，都直接加上作用域限定符`::`，例如：

   ```C++
   namespace wd
   {
       int number = 10;
       void display()
       {
           //cout,endl都是std空间中的实体，所以都加上'std::'命名空间
           std::cout << "wd::display()" << std::endl;
       }
   }//end of namespace wd
   int main(void)
   {
       std::cout << "wd::number = " << wd::number << endl;
       wd::display();
   }
   ```

   好处：准确，只要命名空间中确实有这个实体，就能够准确调用（访问）

   坏处：繁琐

   

2. ==**using编译指令**==

   我们接触的第一个C++程序基本上都是这样的，其中<u>std代表的是标准命名空间</u>。

   ```C++
   #include <iostream>
   using namespace std;
   int main(int argc, char * argv[])
   {
       cout << "hello,world" << endl;
       return 0;
   }
   ```

   其中第二行就使用了using编译指令。如果一个名称空间中有多个实体，使用using编译指令，就会==把该空间中的**所有实体**一次性引入到程序之中==；对于初学者来说，如果对一个命名空间中的实体并不熟悉时，直接使用这种方式，有可能还是会造成名字冲突的问题，而且出现错误之后，还不好查找错误的原因，比如下面的程序就会报错，当然该错误是人为造成的。

   ```C++
   #include <iostream>
   using namespace std;
   double cout()
   {
   	return 1.1;
   }
   int main(void)
   {
       cout();
       return 0;
   }
   ```

   ```c++
   namespace wd
   {
       int num = 10;
   }//end of namespace wd
   void test0(){
   	using namespace wd;
   	cout << num << endl;
   }
   ```

   

3. ==**using声明机制**==

​	using声明机制的作用域是**从using语句开始，到using所在的作用域结束**。要注意，在同一作用域内用using声明的不同的命名空间的成员不能有同名的成员，否则会发生重定义。

```C++
#include <iostream>
using std::cout;
using std::endl;
namespace wd
{
    int number = 10;
    void display()
    {
        cout << "wd::display()" << endl;
    }
}//end of namespace wd
using wd::number;
using wd::display;
int main(void)
{
    cout << "wd::number = " << number << endl;
    wd::display();
}
```

在这三种方式之中，我们推荐使用的就是第三种，不是一次性引入所有实体，而是用什么申明什么，需要哪个实体的时候就引入到程序中，不需要的实体就不引入，尽可能减小犯错误的概率。



###  命名空间的嵌套使用

类似于文件夹下还可以建立文件夹，命名空间中还可以定义命名空间。那么内层命名空间中的实体如何访问呢？尝试一下

```cpp
namespace wd
{
    int num = 100;
    void func()
    {
        cout << "func" << endl;
    }

    namespace cpp
    {
        int num = 200;
        void func()
        {
            cout << "cpp::func" << endl;
        }
    }//end of namespace cpp
}//end of namespace wd

void tst0()
{
	cout << wd::cpp:num << endl;
	wd::cpp::func();
}

void test1()
{
	using namespace wd::cpp;
	cout << num <<endl;
	func();
}

void test2()
{
	using wd::cpp::num;
	using wd::cpp::func;// 只写函数名字
	cout << num << endl;
	func();
}
```



### 匿名命名空间

命名空间还可以不定义名字，不定义名字的命名空间称为匿名命名空间（简称匿名空间），其定义方式如下：

```C++
int  num = 100;
namespace
{
    // 在c++中可以直接使用c语言的函数都已经定义在匿名空间中了
    // 如果认为的在匿名空间中重新定义，在调用时会有新的逻辑，不建议这样改写，会造成混乱
    //void printf(const char * str, int a){
    //    cout << str << endl;
    //    cout << a <<endl;
    //} 
    int num = 10;
    void func(){
        cout << "func()" << endl;
    }
}//end of anonymous namespace

void test0()
{
    // cout << num <<endl;
    // func();
    cout << ::num <<endl;// 100
    ::func();// func()
    ::printf("%d\n",num);//printf已包含在匿名空间中
}
```

在C++代码中可以直接使用一些C语言的函数，就是通过匿名空间实现（体现了C++对C的兼容性），在本文件使用匿名命名空间的实体时不必用命名空间限定。

在单一的源文件中，匿名空间中定义的实体类似于定义在全局位置的实体。

使用匿名空间实体时，可以直接使用，也可以加上作用域限定符（没有空间名），但是如果匿名空间中定义了和全局位置中重名的实体，会有冲突，即使使用::作用域限定符也无法访问到匿名空间中重名的实体，只能访问到全局的实体。



匿名空间注意事项：

（1）匿名空间不要定义与全局空间中同名的实体；

（2）匿名空间中支持改写兼容C语言的函数，但是最好不要改写；

（3）匿名空间中的实体不能跨模块调用。

补充：匿名空间中的实体只能在本文件的作用域中有效，他的作用域是从匿名命名空间声明开始到本文件的结束。



### 跨模块调用问题

一个`.c` / `.cc` / `.cpp` 的文件可以称为一个模块。

**（1）全局变量和函数是可以跨模块调用的**

```c++
//externA.cc
int num = 100;
void print(){
	cout << "extenA print()" << endl;
}

//externC.cc
int num = 300;
void print(){
	cout << "extenC print()" << endl;
}

//externB.cc
extern int num; // 外部引入申明
extern void printf();
void test0(){
	cout << num << endl;
	printf();
}
```

```
# 联合编译
$ g++ externA.cc externB.cc -----------> 100
$ g++ externC.cc externB.cc -----------> 300
$ g++ externA.cc externB.cc externC.cc -----------> 报错
```

补充：extern外部引入的方式适合管理较小的代码组织

include头文件的方式在代码组织上更清晰，但是会一次引入全部内容，相较而言效率比较低

**（2）有名命名空间中的实体可以跨模块调用**

```c++
//externA.cc
namespace wd
{
    int num = 200;
    void printf(){
        cout << "wd" << endl;
    }
}//end of namespace wd

//externB.cc
namespace wd
{
    extern int num;
    extern void print();
}//end of namespace wd

void test1(){
	// cout << wd::num << endl; //ok
    
	// using wd::num; //ok
	// cout << num << endl;
	
    // using namespace wd;
	// cout << num << endl;
}

```

```
# 联合编译/链接
$ g++ externA.cc externB.cc -----------> 200
两个wd是视为同一个匿名空间，名字要相同
```

命名空间中的实体跨模块调用时，要在新的源文件中在此定义同名的命名空间，进行联合编译时，这两次定义被认为是同一个命名空间

使用规则：如果要同时从<全局位置>和<命名空间中>外部引入实体，要么让他们呢不要重名，要么在使用时采用作用域限定的方式

**（3）静态变量和函数只能在本模块内部使用**

```cpp
//externA.cc
static int num = 100;
static void printf(){
	cout << "static" << endl;
}

//externB.cc
// extern static int num; // error
extern int num;
void test0(){
	cout << num << endl;
}

# 联合编译/链接
$ g++ externA.cc externB.cc -----------> error
两个wd是视为同一个匿名空间，名字要相同
```

**（4）匿名空间的实体只能在本模块内部使用**

匿名空间中的实体只能在本文件的作用域内有效，它的作用域是从匿名命名空间声明开始到本文件结束。

```cpp
externA.cc
namespace
{
    int num2 = 300;
    void printf(){
        cout << "Anonymous printf" << endl;
    }
}//end of anonymous namespace

externB.cc
namespace{
	extern int num;
}//end of anonymous namespace
void test0(){
	cout << num << endl;
}

# 联合编译/链接
$ g++ externA.cc externB.cc -----------> error
```



### 命名空间可以多次定义

函数可以声明多次，但是只能定义一次；命名空间可以多次定义。

```cpp
//multiNamespace.cc
namespace wd
{
	int num = 200;
}//end of anonymous namespace
namespace wd
{
	int num = 300;
	// int num2 = 300; // error
}//end of anonymous namespace
```

在同一个源文件中，可以多次定义同名的匿名空间，被认为是同一个匿名空间，所以不能进行重复定义。

```c++
namespace wd{
    void print()
    {
        cout << "print" << endl;
    }
    // print(); // error
}
```

在命名空间中可以**声明实体，定义实体**，但是**<u>不能使用实体</u>**，匿名空间中的实体一定在命名空间之外，可以理解为命名空间只是用来存放实体。

```c++
#include <iostream>
using std::cout;
using std::endl;

namespace wd
{
    int num;
    // num = 400; //error
    void print(){
        cout << "print()" << endl;
    }
    // print(); //error
}

namespace wd
{
    // int num = 300; //error 编译时报错
    int num2 = 300;
}

using namespace wd;
void test0()
{
    print();
    num = 500;
    cout << num << endl;
}

int main(void)
{
    test0();
    return 0;
}
```



### 总结

**命名空间的作用：**

1. 避免命名冲突：命名空间提供了一种**==将全局作用域划分成更小的作用域==**的机制，用于避免不同的代码中可能发生的命名冲突问题
2. 组织代码：将相关的实体放到同一个命名空间
3. 版本控制：不同版本的代码放到不同的命名空间中

总之，需要用到**代码分隔**的情况就可以考虑使用命名空间。

还有一个隐藏的好处：**声明主权**。



下面引用当前流行的名称空间使用指导原则：

1. 提倡在已命名的名称空间中定义变量，而不是直接定义外部全局变量或者静态全局变量。

2. 如果开发了一个函数库或者类库，提倡将其放在一个名称空间中。

3. 对于 using 声明，首先将其作用域设置为局部而不是全局。

4. 不要在头文件中使用 using 编译指令，这样，使得可用名称变得模糊，容易出现二义性。

5. 包含头文件的顺序可能会影响程序的行为，如果非要使用 using 编译指令，建议放在所有 #include 预编译指令后。



## const关键字

###  ==修饰内置类型*==

内置类型：库函数中的，非自己 class 定义的类型

```C++
const int number1 = 10;
int const number2 = 20;

const int val; //error 常量必须要进行初始化
val = 100; //error const不可赋值
```

**==const修饰==**的变量称为**==常量==**，<u>常量必须要进行==初始化==，之后不能修改其值</u>。



char/short/int/long/float/double  整型、浮点型数据都可以修饰——const常量。

除了这种方式可以创建常量外，还可以使用==**宏定义**（#define）==的方式创建**常量**。

```C++
#define NUMBER 1024
```



> 由此引出一个**面试常考题**：
>
> ==**const常量和宏定义（define）常量的区别* **==：（相同点：它们都可以用来创建常量）
>
> 1. **发生的时机不同**：
>
>    - C语言中的宏定义（define）发生时机在**<u>==预处理==</u>**时，做字符串的替换；
>    - const常量是在**<u>==编译==</u>**时（const常量本质还是一个变量，只是用const关键字限定之后，赋予**<u>只读</u>**属性，使用时依然是以变量的形式去使用）
>
> 2. **类型和安全检查不同**：在使用中，应尽量以 const 替换宏定义，可以减小犯错误的概率。
>
>    - 宏定义（define）没有类型，不做任何类型检查；
>    - const常量有具体的类型，在编译期会执行**==类型检查==**。
>
> 3. 存储方式不同：
>
>    - define宏仅仅是展开，有多少地方使用，就展开多少次，**不会分配内存**。
>
>    - const常量会在**==分配内存==**（**堆**中或者**栈**中）。
>
> 4. const可以**节省空间**，避免不必要的内存分配。例如：
>
>    ```c++
>    #define PI 3.14159 		//常量宏
>    const doulbe Pi=3.14159;//此时并未将Pi放入ROM中 ......
>    
>    double I=PI; //编译期间进行宏替换，分配内存
>    double J=PI; //再进行宏替换，又一次分配内存！ 
>    
>    double i=Pi; //此时为Pi分配内存，以后不再分配！
>    double j=Pi; //没有内存分配
>    ```
>
>    <u>const定义常量从汇编的角度来看，只是==给出了对应的**内存地址**==，而不是像#define一样给出的是**立即数**</u>，所以，const定义的常量在程序运行过程中**只有一份拷贝**（因为是全局的只读变量，存在静态区），而 #define定义的常量在内存中**有若干个拷贝**。 
>
> 5. 提高了效率。编译器通常不为普通const常量分配存储空间，而是将它们保存在**符号表**中，这使得它成为一个编译期间的常量，没有了存储与读内存的操作，使得它的效率也很高。
>
> 6. 宏替换只作替换，不做计算，不做表达式求解。宏预编译时就替换了，**程序运行时，并不分配内存**。



###  ==修饰指针类型*==

三种形式：`const int * p`      `int const * p1`      `int * const p2`

```C++
/*常量指针*/
int number1 = 10;
int number2 = 20;
const int * p1 = &number1; // 1.1常量指针 const在*前面
*p1 = 100; // error 通过p1指针无法修改其所指内容的值
p1 = &numbers; // ok 可以改变p1指针的指向

int const * p2 = &number1; // 1.2常量指针的第二种写法

/*指针常量*/
int * const p3 = &number1; // 2.指针常量 const在*后面
*p3 = 100; // ok 通过p3指针可以修改其所指内容的值
p3 = &number2; // error 不可以改变p1指针的指向

/* 3 */
const int * const p4 = &number1;//两者皆不能进行修改
```

==**理解常量指针和指针常量的区别（重点）**==

**常量指针**：const在*前面，==**不能**通过解引用修改所指向的**值**==，<u>可以修改指向</u>。
常量指针是一个指针，它指向的数据不能通过这个指针被修改。这意味着虽然可以改变这个指针指向的地址，但不能改变它指向的内存单元中的值。声明常量指针时，需要在指针类型前加上const关键字。

**指针常量**：*在const前面，<u>能够通过解引用修改所指向的值</u>，==**不能**修改**指向**==。
指针常量是一个指针，其值（即指针本身）在声明之后不能被改变。这意味着不能使用该指针来修改它指向的内存地址。声明指针常量时，通常会将指针初始化为某个特定地址，并且这个地址在程序运行过程中保持不变。



> - 补充：
>
> **数组指针**：指向一个数组的指针。
> 数组指针是一个<u>指向数组的指针</u>。换句话说，它是一个变量，其值为另一个数组的地址。通过数组指针，可以访问和操作数组中的元素。数组指针通常用于数组的传递和数组的动态分配。
>
> ```c++
> int arr[5] = {1,2,3,4,5};
> int (*p)[5] = &arr; // &arr指向整个数组地址，若不加&只是指向数组第一个元素
> for(int i = 0; i < 5; ++i){
> 	cout << (*p)[i] << endl;
> }
> ```
>
> **指针数组**：是一个数组，元素都是指针。
>
> 指针数组是一个<u>包含指针的数组</u>。每个元素都是指向某个数据的指针。这种结构常用于存储多个指针，便于管理和访问。
>
> ```c++
> int num = 5, num2 = 6, num3 = 7;
> int *p = &num;
> int *p2 = &num2;
> int *p3 = &num3;
> int *arr[3] = {p,p2,p3};
> for(int i = 0; i < 3; i++){
> 	cout << *arr[i] << endl;
> }
> ```
>
> 
>
> **函数指针**
>
> 函数指针是一个<u>指向函数的指针</u>。它存储了函数的入口地址。通过函数指针，可以在程序中灵活地调用不同的函数。
>
> ```c++
> int add(int x, int y){
> 	return x + y;
> }
> void test(){
> 	// 完整形式
> 	int (*p)(int,int) = &add;
> 	cout << (*p)(7,8) << endl;
> 	// 省略形式
> 	int (*p2)(int,int) = add;
> 	cout << p2(7,8) << endl;
> }
> ```
>
> **指针函数**
>
> 指针函数是一个<u>函数类型指针</u>，它指向函数而不是数据。这种指针常用于回调函数和函数表。
>
> ```c++
> int Number = 600;
> int *f(){
> 	int *p = &Number;
> 	return p;
> }
> 
> void test(){
> 	cout << *f() << endl;
> }
> ```
>



## new/delete表达式

### C/C++申请、释放堆空间的方式对比

**C语言中使用malloc/free函数，C++使用new/delete表达式**。

```c++
int *p = (int*)malloc(sizeof(int));
*p = 10;
```

new语句中可以不加参数，初始化为各类型默认值；也可加参数，参数代表要初始化的值。

```C++
int * p = new int(1); // 初始化为传入的参数值
cout << *p << endl;

int *p1 = new int(); // 初始化为该类型的默认值
cout << *p1 << endl;
```

### ==valgrind工具集*==

valgrind是一种开源工具集，它提供了一系列用于调试和分析程序的工具。其中最为常用和强大的工具就是memcheck。它是valgrind中的一个内存错误检查器，它能够对C/C++程序进行内存泄漏检测、非法内存访问检测等工作。

```
sudo apt install g++
```

- 安装完成后即可通过 memcheck 工具查看内存泄漏情况，编译后输入如下指令


```
valgrind --tool=memcheck ./a.out
```

- 如果想要更详细的泄漏情况，如**==造成泄漏的代码定位==**，编译时加上-g表示生成可以用gdb调试的可执行文件	`--leak-check=full`


```
valgrind --tool=memcheck --leak-check=full ./a.out
```

但是这么长的指令使用起来不方便，每查一次就得输入一次。

- 如果需要**==查看静态区的情况==**，还需要	`--show-reachable=yes`

```
valgrind --tool=memcheck --leak-check=full --show-reachable=yes ./a.out
```



**快捷使用方法：**

1. 在home目录下编辑.bashrc文件，改别名

```
alias memcheck='valgrind --tool=memcheck --leak-check=full --show-reachable=yes'
```

2. 重新加载

```
source .bashrc1
```

改写之后，就可以直接使用memcheck指令查看内存泄漏情况

```
memcheck ./a.out
```

```
==78753== LEAK SUMMARY:
==78753==    definitely lost: 4 bytes in 1 blocks
==78753==    indirectly lost: 0 bytes in 0 blocks
==78753==      possibly lost: 0 bytes in 0 blocks
==78753==    still reachable: 0 bytes in 0 blocks
==78753==         suppressed: 0 bytes in 0 blocks

definitely lost	绝对泄露了
indirectly lost	间接泄露了
possibly lost	可能泄露了，基本不会出现
still reachable	没有被回收，但是不确定要不要回收
suppressed		被编译器自动回收了
```

```
c语言编译时可以加入-fsanitize=address检测内存使用错误，如果发现内存错误，AddressSanitizer 会打印出详细的错误报告，帮助开发者定位问题。
```



> 通过new表达式的使用，引申出**常考面试题**
>
> ==**malloc/free 和 new/delete 的区别* **==
>
> 1. malloc/free是**库函数**；new/delect是**表达式**，后两者使用时不是函数的写法
> 2. malloc返回值是**void***；new表达式的返回值是**相应类型的指针**
> 3. malloc申请的空间**不会进行初始化**，获取到的空间是有脏数据的；new表达式申请空间时可以**直接初始化**
> 4. malloc的参数是**字节数**；new表达式不需要传递字节数，会根据相应类型**自动获取空间大小**
>



###  new表达式申请数组空间

new表达式还可以申请数组空间

```C++
int * p = new int[10](); // 10代表数组元素个数
// 写上小括号，确保了对申请空间这片空间进行了初始化
for(int idx = 0; idx != 10; ++idx){
	p[idx] = idx;
}
// int *p2 = new int[3](){1,2,3}; // error
int *p2 = new int[3]{1,2,3}; // ok 初始化的一种形式
int *p3 = new int[3]{1,2}; // ok 1 2 0
// 如果确定好了要存放的元素，可以采用初始化列表，大括号包含要初始化的元素，如果写了小括号，不能往里面传参数

delect p[];
p = nullptr; // 安全回收
delect p2[];
p2 = nullptr; // 安全回收
delect p3[];
p3 = nullptr; // 安全回收
```



### 回收空间时的注意事项 

（1）三组申请空间和回收空间的匹配组合

```
malloc            free
new               delete
new int[5]()      delete[]
```

如果没有匹配，memckeck会爆出错误匹配的信息，实际开发中有可能回收了有用的信息。



（2）安全回收

 delete只是回收了指针指向的空间，但这个指针变量依然还在，指向了不确定的内容（野指针），容易造成错误。所以需要进行安全回收，将这个指针设为空指针。C++11之后使用**nullptr**表示空指针。



## 引用（==重点==）

### 引用的概念

在理解引用概念前，先回顾一下变量名。 变量名实质就是<u>一段连续内存空间的别名</u>。那一段连续的内存空间只能取一个别名吗？ 显然不是，引用的概念油然而生。在C++中，引用是一个<u>已定义变量的别名</u>。

其语法是：

```C++
//定义方式：    类型 & ref = 变量；
int number = 2；
int & ref = number; // 声明引用的同时，必须对引用进行初始化否则编译时报错
```

在使用引用的过程中，要注意以下几点：

1. & 在这里不再是取地址符号，而是**引用符号**。

2. 引用的类型需要和其绑定的变量的类型相同（目前这样使用，学习继承后这一条有所不同）。
3. 声明引用的同时，必须对引用进行**初始化**，否则编译时报错。
4. ==**引用一经绑定，<u>无法更改绑定</u>**==。

### 引用的本质（被限制的指针）

C++中的引用本质上是一种<u>被限制的指针</u>。类似于线性表和栈的关系，栈是被限制的线性表，底层实现相同，只不过逻辑上的用法不同而已。

由于引用是被限制的指针，所以==引用是<u>占据内存</u>的==，占据的大小就是==一个指针的大小==。有很多的说法，都说引用不会占据存储空间，其只是一个变量的别名，但这种说法并不准确。引用变量会占据存储空间，存放的是一个地址，但是编译器阻止对它本身的任何访问，从一而终总是指向初始的目标单元。在汇编里，引用的本质就是“间接寻址”。

可以尝试对引用取址，发现获取到的地址就是引用所绑定变量的地址。

### ==引用与指针的联系与区别*==

这是一道非常经典的面试题，请尝试着回答一下：

联系：

1. 引用和指针都有地址的概念，都是用来间接访问变量；

2. 引用的底层还是指针来完成，可以把引用视为一个受限制的指针。

区别：

1. **引用==必须初始化==**，<u>指针可以**不初始化**</u>
2. **引用==不能修改绑定==**，但是<u>指针**可以修改指向**</u>
3. **引用可以就可以理解为==不占据额外空间==**（在代码层面对引用取址，发现取到的地址就是引用所绑定的变量地址）

###  引用的使用场景

#### 引用作为函数的参数（重点）

在没有引用之前，如果我们想通过形参改变实参的值，只有使用指针才能到达目的。但使用指针的过程中，不好操作，很容易犯错。 而引用既然可以作为其他变量的别人而存在，那在很多场合下就可以用引用代替指针，因而也具有更好的可读性和实用性。这就是引用存在的意义。

一个经典的例子就是交换两个变量的值，请实现一个函数，能够交换两个int型变量的值：

```C++
void swap(int x, int y){ // 值传递
    int temp = x;
    x = y;
    y = temp;
}
void swap1(int *x, int *y){ // 指针传递
    int temp = x;
    x = y;
    y = temp;
}
void swap2(int &x, int &y){ // 引用传递
    // 这里实参传给swap3其实就是发生了初始化 int &x = a; int &y = b;
    int temp = x;
    x = y;
    y = temp;
}
```

参数传递的方式包括**值传递**、**指针传递**和**引用传递**。

- 采用**值传递**时，系统会在内存中**==开辟空间==**用来存储形参变量，并将实参变量的值**==拷贝==**给形参变量，即形参变量只是实参变量的副本而已；如果函数传递的是类对象，而该对象占据的存储空间比较大，那发生复制就会造成较大的不必要开销。这种情况下，强烈建议使用引用作为函数的形参，这样会大大提高函数的时空效率。

- 当用**引用**作为函数的参数时，其效果和用指针作为函数参数的效果相当。当调用函数时，函数中的形参就会被当成实参变量或对象的一个别名来使用，也就是说此时函数中对形参的各种操作实际上是对实参本身进行操作，而非简单的将实参变量或对象的值拷贝给形参。

- 使用**指针**作为函数的形参虽然达到的效果和使用引用一样，但当调用函数时仍需要==为**形参指针变量**在内存中分配空间==，也由于指针的灵活更可能导致问题的产生，故在C++中推荐使用引用而非指针作为函数的参数。




不希望函数体通过引用改变传入的变量，那么可以通过==**常引用**作为函数参数==

```c++
void func(const int&x, int &y) {
	// x = 100; // error
	y = 200;
}
```

1. 不会修改值
2. 不会复制（造成不必要的开销）



#### 引用作为函数的返回值

要求：当以引用作为函数的返回值时，==返回的变量其生命周期一定是要<u>大于函数的生命周期</u>==的，即当函数执行完毕时，返回的变量还存在。

目的： 避免复制，节省开销

```C++
int  func(){
    //...
    return a;   //在函数内部，当执行return语句时，<会发生复制>
}

int &func2(){
     //...
    return b;   //在函数内部，当执行return语句时，<不会发生复制>
}
```

```c++
int gNumber = 100;
int func(){
	cout << "gNumber:" << gNumber << endl;
	return gNumber; // 返回类型是int（非引用），return时复制
}
int &fun2(){
	cout << "gNumber:" << gNumber << endl;
	// int &ref = gNumber;
    // return ref; // 100
    return gNumber; // 100
}

void test(){
    cout << func() << endl;
    // cout << &func() << endl; // error
    cout << func2() << endl;
    cout << &func2() << endl;
}

int &func4(){
	int * hNumber = new int(1);
	cout << *hNumber << endl;
	return *hNumber;
}
void test0(){
	cout << func4() << endl; // 输出地址指向的值
	cout << &func4() << endl; // 输出地址
	// delete &func4();	// 也会执行一次func4() 然后被delete清除
	
	// 正确清除方式：
	int &ref = func4();
	ref = 100;
	delete &ref;
}
```

如果函数返回的是一个堆空间变脸的引用，那么这个函数调用一次就会new一次，非常容易造成内存泄漏，所以谨慎选择这种写法，并且要有完善的内存回收机制。

**注意事项**

1. 不要返回<u>局部变量</u>的引用。因为局部变量会在函数返回后被销毁，被返回的引用就成为了"无所指"的引用，程序会进入未知状态。

```C++
int & func()
{
	int number = 1;
    return number;
}
```



2. 不要轻易返回一个<u>堆空间变量</u>的引用，非常容易造成内存泄漏。

```C++
int & func()
{
	int * pint = new int(1);
	return *pint;
}

void test()
{
	int a = 2, b = 4;
	int c = a + func() + b;//内存泄漏
}
```



### 总结

引用总结：

1. 在引用的使用中，单纯给某个变量取个别名没有什么意义，引用的目的主要用于在**==函数参数传递==**中，<u>解决大块数据或对象的传递效率和空间不理想的问题</u>。
2. 用引用传递函数的参数，能保证参数传递中不产生副本，提高传递的效率，还可以通过**==const==**的使用，保证了引用传递的**==安全性==**。
3. 引用与指针的区别是，指针通过某个指针变量指向一个变量后，对它所指向的变量间接操作。程序中使用指针，程序的可读性差；而引用本身就是目标变量的别名，对引用的操作就是对目标变量的操作。可以用指针或引用解决的问题，更推荐使用引用。



## 强制转换

C语言中的强制转换在C++代码中依然可以使用，这种C风格的转换格式非常简单

```C++
TYPE a = （TYPE）EXPRESSION;
```

但是c风格的类型转换有不少的缺点，有的时候用c风格的转换是不合适的，因为它可以在任意类型之间转换，比如你可以把一个指向const对象的指针转换成指向非const对象的指针，把一个指向<u>基类对象</u>的指针转换成指向一个<u>派生类对象</u>的指针，这两种转换之间的差别是巨大的，但是传统的c语言风格的类型转换没有区分这些。

另一个缺点就是，c风格的转换不容易查找，它由一个括号加上一个标识符组成，而这样的东西在c++程序里一大堆。c++为了克服这些缺点，引进了4个新的类型转换操作符，他们是**static_cast**，**const_cast**，**dynamic_cast**，**reinterpret_cast**。

### static_cast

最常用的类型转换符，在正常状况下的类型转换, 用于<u>将一种数据类型转换成另一种数据类型</u>，如把int转换为float

使用形式

```
目标类型 转换后的变量 = static_cast<目标地址>(要转换的变量)
```

好处：不允许非法的转换发生；方便查找

```C++
int iNumber = 100；
float fNumber = 0；
fNumber = (float) iNumber；// C风格
fNumber = static_cast<float>(iNumber);
```

也可以完成指针之间的转换，例如可以将void*指针转换成其他类型的指针

```C++
void * pVoid = malloc(sizeof(int));
int * pInt = static_cast<int*>(pVoid);
*pInt = 1;
```

但<u>不能完成任意两个指针类型间的转换</u>

```C++
int iNumber = 1;
int * pInt = &iNumber;
float * pFloat = static_cast<float *>(pInt);//error
```

```c++
const char *pstr = "hello";
int *p = static_cast<int*>(pstr); // 非法的转换，error
```

总结，static_cast的用法主要有以下几种：

1）用于基本数据类型之间的转换，如把int转换成char，把int转换成enum。这种转换的安全性需要开发人员来保证；

2）把void指针转换成目标类型的指针，但不安全；

3）把任何类型的表达式转换成void类型；

4）用于类层次结构中基类和子类之间指针或引用的转换（后面学）。

```c++
const int num = 100;
// int *pInt = &num; // error
const int *pInt = &num;
```

### const_cast        

该运算符用来修改类型的const属性，**基本不用**。

常量指针被转化成非常量指针，并且仍然指向原来的对象；

常量引用被转换成非常量引用，并且仍然指向原来的对象；

常量对象被转换成非常量对象。

```C++
const int number = 100;
int * pInt = &number;//error
int * pInt2 = const_cast<int *>(&number);

*pInt2 = 1000; // 这里修改的数据并<没有写入内存>，而是<放入寄存器中>
// 值不同
cout << *pInt << endl;
cout << number << endl;
// 地址相同
cout << pInt2 << endl;
cout << &number << endl;
```

dynamic_cast：该运算符主要用于基类和派生类间的转换，尤其是向下转型的用法中（后面讲） 

reinterpret_cast：功能强大，慎用（也称为万能转换）

该运算符可以用来处理无关类型之间的转换，即用在任意指针（或引用）类型之间的转换，以及指针与足够大的整数类型之间的转换。由此可以看出，reinterpret_cast的效果很强大，但错误的使用reinterpret_cast很容易导致程序的不安全，==只有将转换后的类型值转换回到其原始类型，这样才是正确使用reinterpret_cast方式==。



## 函数重载

在实际开发中，有时候需要实现几个功能类似的函数，只是细节有所不同。 如交换两个变量的值，但这两种变量可以有多种类型，short, int, float等。在C语言中，必须要设计出不同名的函数，其原型类似于：

```C
void swap1(short *, short *);
void swap2(int *, int *);
void swap3(float *, float *);
```

但在C++中，这完全没有必要。C++ 允许多个函数拥有相同的名字，只要它们的参数列表不同就可以，这就是**函数重载**（Function Overloading）。借助重载，一个函数名可以有多种用途。**函数重载**是指在同一作用域内，可以有一组具有相同函数名，不同参数列表的函数，这组函数被称为重载函数。重载函数通常用来命名一组功能相似的函数，这样做减少了函数名的数量，对于程序的可读性有很大的好处。

注意：C 语言中不支持函数重载，C++才支持函数重载。

###  实现函数重载的条件

**函数==参数==的<u>数量、类型、顺序</u>任一不同则可以构成重载。**

返回值类型不同，参数完全相同，不可以发生重载

### 函数重载的实现原理

实现原理：  名字改编(name mangling)——当函数名称相同时 ，会根据参数的类型、顺序、个数进行改编

- g++ -c Overload.cc

- ==nm Overload.o==

查看目标文件，可以发现原本的函数名都被改编成与参数相关的函数名。                                                    

### extern "C"

在C/C++混合编程的场景下，如果在C++代码中想要**按照C的方式编译函数**应该怎么办？

```C++
extern "C" void func() //用 extern"C"修饰单个函数
{

}

//如果是多个函数都希望用C的方式编译
//或是需要使用C语言的库文件
//都可以放到如下{}中
extern “C”
{
//…… 按照c的方式进行编译
}

```

假如这段代码<u>用c的编译器进行编译</u>，extern "C"{}是不能被识别的，会出现问题，所以可以用如下的宏包裹起来

```
#ifdef __cplusplus
extern "C"{
#endif
...
...
#ifdef __cplusplus
}
#endif
```



## 默认参数

### 默认参数的目的

C++可以给函数定义默认参数值。通常，调用函数时，要为函数的每个参数给定对应的实参。

```C++
void func1(int x, int y)
{
    cout << "x = " << x << endl;
    cout << "y = " << y << endl;
}
```

无论何时调用func1函数，都必须要给其传递两个参数。但C++可以给参数定义默认值，如果将func1函数参数中的x定义成默认值0， y定义成默认值0，只需简单的将函数声明改成

```C++
void func1(int x = 0, int y = 0);
```

这样调用时，若不给参数传递实参，则func1函数会按指定的默认值进行工作。允许函数设置默认参数值，是为了让编程简单，让编译器做更多的检查错误工作。

```c++
int add(int x = 12, int y = 1){
    return x+y;
}

void test(){
    cout << add(24,30) << endl; // 53
    cout << add(100) << endl; // 101
    cout << add() << endl; // 13
}
```

### 默认参数的声明

**一般默认参数在函数声明中提供。**当一个函数既有声明又有定义时，只需要在其中一个中设置默认值即可。若在定义时而不是在声明时置默认值，那么函数定义一定要在函数的调用之前。因为声明时已经给编译器一个该函数的向导，在定义时设默认值时，编译器只有检查到定义时才知道函数使用了默认值。若先调用后定义，在调用时编译器并不知道哪个参数设了默认值。

```C++
//这样可以编译通过
void func(int x,int y);

void test0(){
   func(1,2);
}

void func(int x,int y){
    cout << x + y << endl;                                              
}
```

```C++
//这样无法缺省调用
void func(int x,int y);

void test0(){
    func();//error
    func(100);
}

void func(int x = 0,int y = 0){
    cout << x + y << endl;                                              
}
```

所以我们==通常是将默认值的设置放在声明中而不是定义中==。

```c++
int multiply(int x = 100, int y = 50);
int multiply(int x, int y){
	return x*y;
}
void test1(){
	cout << multiply() << endl;
}
```

如果在申明中和定义中都传了默认值，会报错。

### 默认参数的顺序规定

如果一个函数中有多个默认参数，则形参分布中，默认参数应从右至左逐渐定义。当调用函数时，只能向左匹配参数。如：

```C++
void func2(int a = 1, int b, int c = 0, int d);//error
void func2(int a, int b, int c = 0, int d = 0);//ok
```

若给<u>某一参数设置了默认值，那么在参数表中其后所有的参数都必须也设置默认值</u>，否则，由于函数调用时可不列出已设置默认值的参数，编译器无法判断在调用时是否有参数遗漏。

完成函数默认参数的设置后，该函数就可以按照相应的缺省形式进行调用。

```c++
// 当后面的参数没有默认参数时，前面的参数也不能有默认参数
int add(int x, int y = 1){
	return x + y;
}
```

==总结：函数参数赋默认值从右到左（严格）==。

### 默认参数与函数重载

默认参数可将一系列简单的重载函数合成为一个。例如：

```C++
void func3();
void func3(int x);
void func3(int x, int y);
//上面三个函数可以合成下面这一个
void func3(int x = 0, int y = 0);
```

如果一组重载函数（可能带有默认参数）都允许相同实参个数的调用，将会引起调用的二义性。

```C++
void func4(int x);
void func4(int x, int y = 0);

func4(1);//error,无法确定调用的是哪种形式的func4
```

所以在函数重载时，要谨慎使用默认参数。

```c++
int add(int x, int y = 1){
	return x + y;
}
int add(int x){
	return x;
}
void test(){
	cout << add(24,30) << endl;
	// 缺省调用
	// cout << add(100) << endl;
}
```

重载是允许的，但是在缺省调用时会产生冲突。



## bool类型

bool类型是在C++中一种基本类型，用来表示true和false。true和false是字面值，可以通过转换变为int类型，true为1，false为0.

```C++
int x = true;// 1
int y = false;// 0
```

任何数字或指针值都可以隐式转换为bool值。

任何非零值都将转换为true，而零值转换为false（注意：**-1也是代表true**）

```C++
bool b1 = -100;
bool b2 = 100;
bool b3 = 0;
bool b4 = 1;
bool b5 = true;
bool b6 = false;
int x = sizeof(bool);//x = 1
```

bool变量占1个字节的空间。



## inline函数

在C++中，通常定义以下函数来求取两个整数的最大值

```C++
int max(int x, int y)
{
	return x > y ? x : y;
}
```

为这么一个小的操作定义一个函数的好处有：

- 阅读和理解函数 max 的调用，要比读一条等价的条件表达式并解释它的含义要容易得多;

- 如果需要做任何修改，修改函数要比找出并修改每一处等价表达式容易得多;

- 使用函数可以确保统一的行为，每个测试都保证以相同的方式实现;

- 函数可以重用，不必为其他应用程序重写代码。


虽然有这么多好处，但是写成函数有一个潜在的缺点：**调用函数比求解等价表达式要慢得多**。

在大多数的机器上，调用函数都要做很多工作：<u>调用前要先保存寄存器，并在返回时恢复，复制实参，程序还必须转向一个新位置执行</u>。即对于这种简短的语句使用函数开销太大。

在C语言中，我们使用带参数的宏定义这种借助编译器的优化技术来减少程序的执行时间，请**定义一个==宏==完成以上的max函数的功能**。

```C
#define MAX(x, y)((x)>(y)?(x):(y))
```

那么在C++中有没有相同的技术或者更好的实现方法呢？答案是有的，那就是**==内联(inline)函数==**。内联函数作为编译器优化手段的一种技术，在降低运行时间上非常有用。

### 什么是内联函数

内联函数是C++的增强特性之一，用来降低程序的运行时间。当内联函数收到编译器的指示时，即可发生内联：==编译器将使用**函数的定义体**来替代**函数调用语句**，这种替代行为发生在**<u>编译阶段</u>**而非程序运行阶段==。定义函数时，在函数的最前面以关键字“**inline**”声明函数，该函数即可称为内联函数（内联声明函数）。

```C++
inline int max(int x, y) {
	return x > y ? x : y;
}
```

### 宏函数(#define)与内联函数(inline)

在C程序中，可以用宏代码提高执行效率。宏代码本身不是函数，但是看起来像函数。编译预处理器用拷贝宏代码的方式取代函数调用，省去了参数压栈、生成汇编语言的CALL调用、返回参数、执行return等过程，从而提高了速度。

使用宏代码最大的缺点是**容易出错**，预处理器在拷贝宏代码时常常产生意向不到的边际效应。例如：

```C++
#define MAX(a, b) (a) > (b) ? (a) : (b) 
int result = MAX(20,10) + 20//result的值是多少？
int result2 = MAX(10,20) + 20//result2的值是多少？
//result = MAX(i, j) + 20; 将被预处理器扩展为: result = (i) > (j) ?(i):(j)+20
```

可以修改宏代码为：

```C++
#define MAX(a, b) ((a) > (b) ? (a) : (b))
```

可以解决上面的错误了，但也不是万无一失的，例如：

``` c++
int i = 4,j = 3;
result = MAX(i++,j);
cout << result << endl; //result = 5；
cout << i << endl; //i = 6;

//使用MAX的代码段经过预处理器扩展后，result = ((i++) > (j) ? (i++):(j));
```

**宏**的另一个缺点就是**不可调试**，但内联函数是可以调试的。内联函数不是也像宏一样进行代码展开吗？怎么能够调试呢？其实内联函数的”可调试“不是说展开后还能调试，而是在程序的调试（Debug）版本里它根本就没有真正内联，编译器像普通函数那样为它生成含有调试信息的可执行代码。在程序的发行（Release)版本里，编译器才会实施真正的内联。



那C++的内联函数是如何工作的呢？

对于任何内联函数，编译器在**符号表**（符号表是编译器用来收集和保存字面常量和某些符号常量的地方）里放入函数的声明，包括名字、参数类型、返回值类型。如果编译器没有发现内联函数存在错误，那么该函数的代码也会被放入符号表里。在调用一个内联函数时，编译器首先检查调用是否正确（进行类型安全检查，或者进行自动类型转换）。如果正确，内联函数的代码就会直接替换函数调用语句，于是省去了函数调用的开销。这个过程与预处理有显著的不同，因为预处理器不能执行类型安全检查和自动类型转换。

==**内联函数**就是在普通函数定义之前加上inline关键字==

1. inline是一个建议，并不是强制性的，后面会学到inline失效的情况
2. inline的建议如果有效，就会在==编译时==展开，可以理解为是一种更高级的代码的替换机制（类似于宏-预处理）
3. 函数体内容如果太长，或者有循环之类的结构，不建议inline，以免造成代码膨胀。比较短小的代码适合用内联。

C++的**函数内联机制**既具备宏代码的效率，又增加了安全性，而且可以自由操作类的数据成员，所以在C++中应尽可能的用内联函数取代宏函数。



**对比总结：**

- 宏函数

  - 优点：只是进行**字符串的替换**（==预处理时==），并没有函数的开销，对于比较短小的代码适合使用；

  - 缺点：**==没有==类型检查**，存在安全隐患，而且比较容易写错。


如果使用普通函数的方式又会增加开销，所以一些时候可以采用内联函数（结合了宏函数和普通函数的优点）。

- inline函数：本质也是**字符串替换**（==编译时==），所以不会增加开销；但是**==有类型检查==**，比较安全。




### 内联函数注意事项

1. **如果要把inline函数声明在头文件中，则必须把函数定义也写在头文件中**。若头文件中只有声明没有实现，被认为是没有定义替换规则。如下，foo函数不能成为内联函数：

``` c++
inline void foo(int x, int y);//该语句在头文件中

void foo(int x, int y)//实现在.cpp文件中
{ //... }
```

因为编译器在调用点内联展开函数的代码时，必须能够找到 inline函数的定义才能将调用函数替换为函数代码，而对于在头文件中仅有函数声明是不够的。

当然内联函数定义也可以放在源文件中，但此时只有定义的那个源文件可以用它，而且需要为每个源文件拷贝一份内联函数的定义(每个源文件里的定义必须是完全相同的)。相比之下，放在头文件中既能够确保调用函数是定义是相同的，又能够保证在调用点能够找到函数定义从而完成内联(替换)。

![image-20240306221654524](./cppbase.assets/image-20240306221654524.png)

从测试文件出发，找到头文件，发现此函数是inline函数，那么要展开替换，必须要有明确的替换规则，但是在头文件中并没有发现替换规则，所以报错出现未定义的问题。

==inline函数在头文件中，必须有定义。==



2. **谨慎使用内联**

内联能提高函数的执行效率，为什么不把所有的函数都定义成内联函数？事实上，内联不是万灵丹，它以代码膨胀（拷贝）为代价，仅仅省去了函数调用的开销，从而提高程序的执行效率（注意：这里的“函数调用开销”是指参数压栈、跳转、退栈和返回等操作）。

如果执行函数体内代码的时间比函数调用的开销大得多，那么 inline 的效率收益会很小。

另外，每一处内联函数的调用都要拷贝代码，将使程序的总代码量增大，消耗更多的内存空间。

以下情况不宜使用内联：

- 如果函数体内的代码比较长，使用内联将导致可执行代码膨胀过大。

- 如果函数体内出现循环或其他复杂的控制结构，那么执行函数体内代码的时间将比函数调用开销大得多，因此内联的意义并不大。



实际上，inline 在实现的时候就是对编译器的一种请求，因此编译器完全有权利取消一个函数的内联请求。一个好的编译器能够根据函数的定义体，自动取消不值得的内联，或自动地内联一些没有inline 请求的函数。因此编译器往往选择那些短小而简单的函数来内联。



## 异常处理

异常是程序在执行期间产生的问题。C++ 异常是指在程序运行时发生的特殊情况，比如尝试除以零的操作。异常提供了一种转移程序控制权的方式。C++ 异常处理涉及到三个关键字：**try**、**catch**、**throw**。



抛出异常即检测是否产生异常，在 C++ 中，其采用 **throw 语句**来实现，如果检测到产生异常，则抛出异常。该语句的格式为：

``` c++
throw 表达式;
```

- 先定义抛出异常的规则（throw）,异常是一个表达式，它的值可以是基本类型，也可以是类；

```C++
double division(double x, double y)
{
	if(y == 0) throw "Division by zero condition!";
	return x / y;
}
```



**try-catch语句块**的语法如下：

``` c++
try {
	//语句块
} catch(异常类型) {
	//具体的异常处理...
}
...
catch(异常类型) {
	//具体的异常处理...
}
```

 try-catch语句块的catch可以有多个，至少要有一个，否则会报错。



- 执行 try 块中的语句，如果执行的过程中没有异常拋出，那么执行完后就执行最后一个 catch块后面的语句，所有 catch 块中的语句都不会被执行；
- 如果 try 块执行的过程中拋出了异常，那么拋出异常后立即跳转到第一个“异常类型”和拋出的异常类型匹配的 catch 块中执行（称作异常被该 catch 块“捕获”），执行完后再跳转到最后一个catch 块后面继续执行。

注意：catch的是类型，不是具体信息。



```c++
double division(double x,double y){
	if(y == 0){
		throw "Deivision by zero"; // 抛出，函数终止
	}
	if(x == 0){
		throw x;
	}
	return x/y;
}

void test(){
	double x = 100, y = 0;
    try{
    	cout << division(y,x) << endl;
    }catch(const char *msg){
    	cout << "hello," << msg << endl;
    }catch(double x){
    	cout << "double" << endl;
    }catch(int x){
    	cout << "int" << endl;
    }
    cout << "over" << endl;
}
```



## 内存布局

64位系统，理论空间达到16EB（2^64）,但是受硬件限制，并不会达到这么多；

以32位系统为例，一个进程在执行时，能够访问的空间是**虚拟地址空间**。理论上为2^32，即4G，有1G左右的空间是内核态，剩下的3G左右的空间是用户态。从高地址到低地址可以分为五个区域：

- 栈区：操作系统控制，由高地址向低地址生长，编译器做了优化，显式地址时栈区和其他区域保持一致的方向。

- 堆区：程序员分配，由低地址向高地址生长，堆区与栈区没有明确的界限。

- 全局/静态区：读写段（数据段），存放全局变量、静态变量。

- 文字常量区：只读段，存放程序中直接使用的常量，如 `const char * p = "hello";`  hello 这个内容就存在文字常量区。

- 程序代码区：只读段，存放函数体的二进制代码。

![image-20240306221942777](./cppbase.assets/image-20240306221942777.png)

![image-20240306222003576](./cppbase.assets/image-20240306222003576.png)



## C风格字符串

如果用数组形式，注意留出一位给终止符；

如果用指针形式，直接定义为 const char * ，C++ 代码中标准 C 风格字符串的写法。

输出流运算符默认重载，cout 利用输出流运算符接 char 型数组名、指针名时，输出的是内容，而不是地址。

<img src="./cppbase.assets/image-20240306222013776.png" alt="image-20240306222013776" style="zoom: 67%;" />



# 第二章 类与对象基础

## 面向对象思想

**过程论**认为：数据和逻辑是分离的、独立的，程序世界本质是过程，数据作为过程处理对象，逻辑作为过程的形式定义，世界就是各个过程不断进行的总体。

**对象论**认为：数据和逻辑不是分离的，而是相互依存的。相关的数据和逻辑形成个体，这些个体叫做对象，世界就是由一个个对象组成的。对象具有相对独立性，对外提供一定的服务。所谓世界的演进，是在某个“初始作用力”作用下，对象间通过相互调用而完成的交互；在没有初始作用力下，对象保持静止。这些交互并不是完全预定义的，不一定有严格的因果关系，对象间交互是“偶然的”，对象间联系是“暂时的”。世界就是由各色对象组成，然后在初始作用力下，对象间的交互完成了世界的演进。过程论和对象论不是一种你死我活的绝对对立，而是一种辩证统一的对立，两者相互渗透、在一定情况下可以相互转化，是一种“你中有我、我中有你”的对立。如果将对象论中的所有交互提取出来而撇开对象，就变成了过程论，而如果对过程论中的数据和逻辑分类封装并建立交互关系，就变成了对象论。

过程论相对确定，有利于明晰演进的方向，但当事物过于庞大繁杂，将很难理清思路。因为过程繁多、过程中又有子过程，容易将整个世界看成一个纷繁交错的过程网，让人无法看清。对象论相对不确定，但是因为以对象为基本元素，即使很庞大的事物，也可以很好地分离关注，在研究一个对象的交互时，只需要关系与其相关的少数几个对象，不用总是关注整个流程和世界，**对象论更有助于分析规模较大的事物**。但是，对象论也有困难。例如，如何划分对象才合理？对于同一个驱动力，为什么不同情况下参与对象和交互流程不一样？如何确定？其实，这些困难也正是面向对象技术中的困难。



## 类的定义

C++用类来描述对象，类是对现实世界中相似事物的抽象，比如同是“双轮车”的摩托车和自行车，有共同点，也有许多不同点。“车”类是对摩托车、自行车、汽车等相同点的提取与抽象。

类的定义分为两个部分：

1. 数据，相当于现实世界中的**属性**，称为**数据成员**；

2. 对数据的操作，相当于现实世界中的**行为**，称为**成员函数**。

有些地方，会将类的<u>数据成员</u>和<u>成员函数</u>统称为类的<font color=red>**成员**</font>。

从程序设计的观点来说，**类就是数据类型**，是用户定义的数据类型，对象可以看成某个类的实例（某类的变量）。所以说<u>类是对象的抽象，对象是类的实例</u>。

C++中用关键字`class`来定义一个类，其基本形式如下：类的定义和声明

```C++
class MyClass{//类的定义 大驼峰规则
    //……
    void myFunc(){}  //成员函数 小驼峰规则
    int _a;          //数据成员 下划线规则
};

//类也可以先声明，后完成定义
class MyClass2;//类的声明

class MyClass2{//类的定义
    //……
};//分号不能省略
```

### 访问修饰符（public/protected/private）

如下，我们定义好一个Computer的类，假设我们站在代工厂的视角，这个Computer类拥有两个属性——品牌与价格；两个行为——设置品牌与设置价格。

``` c++
class Computer {
	void setBrand(const char * brand)
	{
		strcpy(_brand, brand);
	}
    
	void setPrice(float price)
	{
		_price = price;
	}
    
	char _brand[20];
	float _price;
}；
```

按之前的理解，现在我们自定义了一个新的类——Computer类，我们需要实例化出一个对象（特定的Computer），再通过这个对象来访问数据成员或调用成员函数，如下：

```c++
Computer pc;
pc.setPrice(10000); //error
pc._price; //error
```

结果发现都会报错，这是什么原因呢？事实上，class中的所有的成员都拥有自己的访问权限，分别可以用以下的三个访问修饰符进行修饰

- **==public:==**		//公有的访问权限，在<u>==类外==</u>可以通过对象直接访问公有成员
- **==protected:==** //保护的访问权限，<u>==派生类中可以访问==</u>，在类外不能通过对象直接访问（后面学）
- **==private:==**      //私有的访问权限，在本类之外不能访问，比较敏感的数据设为 private

**注意：**

- 类定义中访问修饰符的管理范围<u>从当前行到下一个访问修饰符或类定义结束</u>；
- class定义中如果在成员定义（或声明）之前没有任何访问修饰符，其**默认的访问权限为<u>==私有==</u>**。

``` c++
class Computer {
public:
	void setBrand(const char * brand)
	{
		strcpy(_brand, brand);
	}
	void setPrice(float price)
	{
		_price = price;
	}
private:
	char _brand[20];
	float _price;
}；
    
int main(){
    Computer pc;
    pc.setPrice(10000); //ok
    // pc._price; //error,因为_price是私有的
}
```

### struct与class的对比

学习了类的定义后，我们会发现它与C语言中的struct很相似。

- **C语言中的struct**

回顾一下C语言中struct的写法

``` c++
struct Student{
    int number;
    char name[25];
    int score;
};

void test0(){
    struct Student s1;
    struct Student s2;
}
```

采用 typedef 取别名后更像 C++ 的 class

``` c++
typedef struct{
    int number;
    char name[25];
    int score;
} Student;

void test0(){
    Student s1;
    Student s2;
}
```

C中的struct只能是一些变量的集合体，可以封装数据但不能隐藏数据，而且成员不能是函数，要使用函数只能使用函数指针的方式。访问权限限制、继承性、构造析构都没有。事实上，C中struct的这种封装属于广义上的封装。面向对象的封装是指**隐藏对象的属性和实现细节，仅对外公开接口，控制在程序中属性的读和修改的访问级别；将抽象得到的数据和操作数据的方法相结合，形成“类”**。



**结论：**

- **C++中的struct**
  C++中的struct对C中的struct做了拓展，基本等同于class，默认访问权限是==**public**==。

- **C++中的class**
  class默认访问权限是==**private**==。



### 成员函数的定义

- **成员函数定义的形式**

成员函数可以在类内部完成定义，也可以在类内部只进行声明，在类外部完成定义。

``` c++
// 假如有自定义的头文件，放在最上面
#include <string.h> // c的头文件放在上面
#include <iostream> // c++的头文件放在下面
// 假如有第三方的库，放在最下面

using std::cout;
using std::endl;

class Computer {
public:
	//成员函数
	void setBrand(const char * brand);//设置品牌
	void setPrice(float price);//设置价格
    void print();//打印信息
private:
	//数据成员
	char _brand[20];
	float _price;
};

// 最然<成员函数的定义>放在了类之外
// 但是由于有作用域限定，仍视为类中
// 格式:	返回类型 类名::成员函数名
void Computer::setBrand(const char * brand)
{ 
    strcpy(_brand, brand);
}
void Computer::setPrice(float price)
{ 
    _price = price;
}
void Computer::print(){
    cout << "brand:" << _brand << endl;
    cout << "price:" << _price << endl;
}
void test(){
    Computer pc;
    pc.setBreadn("Apple");
    pc.setPrice(999.9);
    pc.print();
}
```

实际开发中为什么采用成员函数声明和实现分离的写法？

当类中成员函数比较多（复杂），不容易看，如果只在类中进行成员函数的声明（同时配上注释），会方便理解。这是工作中常用的写法。



- ==**多文件联合编译时可能出现的错误**==

为什么一般不在头文件中定义函数？

在头文件中定义一个函数时，如果多个源文件都包含了该头文件，并且这些源文件中都调用了该函数，那么在联合编译时会出现**重定义错误**。因为头文件的内容在每个源文件中都会被复制一份，而每个源文件都会生成对应的目标文件。在链接的阶段，会出现多个相同函数定义的情况，导致重定义错误。

```c++
/* g++ test.cc test2.cc 重定义问题 */
//test.hpp
void print(){
	cout << "hello" << endl;
}

//test.cc
#include "test.hpp"
void main(){
	print();
}

//test2.cc
#include "test.hpp"
void main(){
	print();
}
```

对于成员函数，也存在这样的问题。

**如果在头文件中采用成员函数声明和定义分离的形式，在类外部完成成员函数的实现，就会陷入这个错误。**

解决方法1：在成员函数的定义前加上 **`inline` 关键字**，inline函数定义在头文件中是ok的



解决方法2：将成员函数放到**类内部**进行定义（<font color=red>**说明类内部定义的成员函数就是inline函数**</font>）



解决方法3：<span style=color:red;background:yellow>**函数声明放在头文件，函数定义放在实现文件中**</span>，就算有多个测试文件，也不会出现重定义（最常用的方式）。之后遇到这种需求（定义一个非常复杂的类，多出都需要用到这个类）



## 对象的创建（构造函数）

在之前的 Computer 类中，通过自定义的公共成员函数 setBrand 和 setPrice 实现了对数据成员的初始化。实际上，C++ 为类提供了一种<span style=color:red;background:yellow>**特殊的成员函数——构造函数**</span>来完成相同的工作。

- 构造函数的作用：就是用来初始化数据成员的

- 构造函数的形式：

  <span style=color:red;background:yellow>**没有返回值，即使是void也不能有；**</span>

  <span style=color:red;background:yellow>**函数名与类名相同，再加上函数参数列表。**</span>

构造函数在对象创建时<font color=red>**自动调用**</font>，用以完成对象成员变量等的初始化及其他操作(如为指针成员动态申请内存等)



### 对象的创建规则

1. 当类中没有显式定义构造函数时 ，编译器会自动生成一个**默认  (无参)  构造函数** ，但并不会初始化数据成员；

   以Point类为例：

   ```C++
   class Point {
   public:
   	void print()
   	{
   		cout << "(" << _ix 
               << "," << _iy
   			<< ")" << endl;
   	}
   private:
   	int _ix;
   	int _iy;
   };
   
   void test0()
   {
   	Point pt;
   	pt.print();
   }
   //运行结果显示，pt的_ix,_iy都是不确定的值
   ```

   

2. 一旦当**类中显式提供了构造函数**时 ，编译器就不会再自动生成默认的构造函数；

   ```C++
   class Point {
   public:
       Point(){
           cout << "Point()" << endl;
           _ix = 0;
           _iy = 0;
       }
   	void print()
   	{
   		cout << "(" << _ix 
               << "," << _iy
   			<< ")" << endl;
   	}
   private:
   	int _ix;
   	int _iy;
   };
   
   void test0()
   {
   	Point pt;
   	pt.print();
   }
   //这次创建pt对象时就调用了自定义的构造函数，而非默认构造函数
   ```

   

3. 编译器自动生成的默认构造函数是无参的，**构造函数也可以接收参数**，在对象创建时提供更大的自由度；

   ``` c++
   class Point {
   public:
       Point(int ix, int iy){
           cout << "Point(int,int)" << endl;
           _ix = ix;
           _iy = iy;
       }
   	void print()
   	{
   		cout << "(" << _ix 
               << "," << _iy
   			<< ")" << endl;
   	}
   private:
   	int _ix;
   	int _iy;
   };
   
   void test0()
   {
   	Point pt;//error,没有默认的无参构造函数可供调用
       Point pt2(10,20);
   	pt2.print();
   }
   ```

   

4. 如果**还希望通过默认构造函数创建对象**，  则必须要**手动提供一个默认构造函数**；

   ```C++
   class Point {
   public:
       Point(){}
       
       Point(int ix, int iy){
           cout << "Point(int,int)" << endl;
           _ix = ix;
           _iy = iy;
       }
   	void print()
   	{
   		cout << "(" << _ix 
               << "," << _iy
   			<< ")" << endl;
   	}
   private:
   	int _ix;
   	int _iy;
   };
   
   void test0()
   {
   	Point pt;//ok
       Point pt2(10,20);
   	pt2.print();
   }
   ```

   

5. 构造函数可以**==重载==**

​	如上，一个类中可以有多种形式的构造函数，说明构造函数可以重载。事实上，真实的开发中经常会给一个类定义各种形式的构造函数，以提升代码的灵活性（可以用多种不同的数据来创建出同一类的对象）。

```c++
Point(int x)
{
    _ix = ix;
    _iy = 100;
    cout << "Point(int)" << endl;
}

Point(int x,int y)
{
    _ix = ix;
    _iy = iy;
    cout << "Point(int,int)" << endl;
}
```



### 对象的数据成员初始化（初始化列表）

上述例子中，在构造函数的函数体中对数据成员进行赋值，其实严格意义上不算初始化（而是算赋值）。

在C++中，对于类中数据成员的初始化，推荐使用<span style=color:red;background:yellow>**初始化列表**</span>完成。初始化列表位于<u>构造函数形参列表之后，函数体之前，用**冒号**开始，如果有多个数据成员，再用**逗号**分隔，初始值放在一对**小括号**中</u>。

``` c++
class Point {
public:
    // 初始化列表 严格意义上的初始化
	Point(int ix)
	: _ix(ix)
	, _iy(100)
	{
		cout << "Point(int,int)" << endl;
	}
	// 初始化列表 严格意义上的初始化
	Point(int ix = 0, int iy = 0)
	: _ix(ix)
	, _iy(iy)
	{
		cout << "Point(int,int)" << endl;
	}
	//...
};
```

**如果没有在构造函数的初始化列表中显式地初始化成员，则该成员将在构造函数体之前执行默认初始化。**如在“对象的创建规则”示例代码中，有参的构造函数中 _ix 和 _iy 都是先执行默认初始化后，再在函数体中执行赋值操作。



- 构造函数的参数也可以按**从右向左规则**赋默认值，同样的，如果构造函数的声明和定义分开写，只用在声明或定义中的一处设置参数默认值，<u>一般建议在声明中设置默认值</u>。

  ```` c++
  class Point {
  public:
  	Point(int ix, int iy = 0); // 默认参数设置在声明时
  	//...
  };
  
  Point::Point(int ix, int iy)
  : _ix(ix)
  , _iy(iy)
  {
  	cout << "Point(int,int)" << endl;
  }
  
  void test0(){
      Point pt(10);
  }
  ````

  

- ==C++11==之后，普通的数据成员也可以在**声明时就进行初始化**。但一些特殊的数据成员初始化只能在初始化列表中进行，故一般情况下统一推荐在初始化列表中进行数据成员初始化。

``` c++
class Point {
public:
	...
    ...
private:
    int _ix = 0; // C++11
    int _iy = 0;
};
```



- ==数据成员<u>**初始化的顺序**与其**声明的顺序**保持一致</u>==，与它们在初始化列表中的顺序无关（但**初始化列表顺序**一般习惯保持与数据成员**声明的顺序**一致）。



### 对象所占空间大小(sizeof)

之前在讲引用的知识点时，我们提过使用引用作为函数的返回值可以避免多余的复制。内置类型的变量最大也就是long double,占16个字节。但是现在我们学习了类的定义，自定义类型对象的大小可以非常大。

使用**sizeof**查看一个**类的大小**和查看**该类对象的大小**，得到的结果是相同的（**类是对象的模板**）

``` c++
void test0(){
    Point pt(1,2);
    cout << sizeof(Point) << endl;
    cout << sizeof(pt) << endl;
 }
```

成员函数并不影响对象的大小，对象的大小与数据成员有关（后面学习继承、多态，对象的内存布局会更复杂）；

现阶段，在不考虑继承多态的情况下，我们做以下测试。发现有时一个类所占空间大小就是其数据成员类型所占大小之和，有时则不是，这就是因为有<span style=color:red;background:yellow>**内存对齐**</span>的机制。

```C++
class A{
    int _num;
    double _price;
};
//sizeof(A) = 16

class B{
    int _num;
    int _price;
};
//sizeof(B) = 8
```



- **为什么要进行内存对齐？**

  1.平台原因(移植原因)：不是所有的硬件平台都能访问任意地址上的任意数据的；某些硬件平台只能在某些地址处取某些特定类型的数据，否则抛出硬件异常。

  2.性能原因：CPU 对内存的读取不是连续的，而是分成块读取的，块的大小只能是1、2、4、8、16 ... 字节。若不进行内存对齐，可能需要做两次内存访问，性能会大打折扣；而进行过内存对齐仅需要一次访问。

  ![image-20240223163737665](./cppbase.assets/image-20240223163737665.png)

  64位系统默认以8个字节的块大小进行读取。

  如果没有内存对齐机制，CPU读取_price时，需要两次总线周期来访问内存，第一次读取 _price数据前四个字节的内容，第二次读取后四个字节的内容，还要经过计算，将它们合并成一个数据。

  有了内存对齐机制后，以浪费4个字节的空间为代价，读取_price时只需要一次访问，所以编译器会隐式地进行内存对齐。

  **规则一：**

  <span style=color:red;background:yellow>**按照类中占空间最大的数据成员大小的倍数对齐；**</span>

  

  如果数据成员再多一些，我们发现自定义类型所占的空间大小还与这些数据成员的顺序有关

  ```C++
  class C{
      int _c1;
      int _c2;
      double _c3;
  };
  //sizeof(C) = 16
  
  class D{
      int _d1;
      double _d2;
      int _d3;
  };
  //sizeof(D) = 24
  ```

  <img src="./cppbase.assets/image-20240223163111420.png" alt="image-20240223163111420"  />

  

  如果数据成员中有**数组类型**,会按照<u>除数组以外的其他数据成员中最大的那一个的倍数对齐</u>

  ```C++
  class E{
      char _eArr[20];
      double _e1;
      int _e2;
  };
  // sizeof(E) = 40
  
  class F{
      char _fArr[20];
  };
  // sizeof(F) = 20
  ```

  再判断一下，G类所占的空间是多少？

  ```C++
  class G{
      char _gArr[20];
      int _g1;
      double _g2;
  };
  // sizeof(G) = 32
  ```

在C语言的涉及的结构体代码中，我们可能会看到#pragma pack的一些设置，**==#pragma pack(n)==**即<u>设置编译器按照**n个字节**对齐，n可以取值1,2,4,8,16</u>.在C++中也可以使用这个设置，最终的对齐效果将按照 #pragma pack 指定的数值和类中最大的数据成员长度中，比较小的那个的倍数进行对齐。

```c++
#include ...
using ...
#pragna pack(16)

class A{
	...
}
```

总结：

除数组外，其他类型的数据成员中，以较大的数据成员所占空间的倍数去对齐。

内存对齐还与数据成员的声明顺序有关。



### 指针数据成员（申请/释放堆空间）

```c++
const char *p = "hello";
cout << sizeof(*p) << endl; // 1 h
cout << sizeof(p) << endl;  // 8 64位地址->指针8字节
cout << strlen(p) << endl;  // 5
```

类的数据成员中有指针时，意味着创建该类的对象时要进行**指针成员的初始化**，需要**申请堆空间**。

**在初始化列表中申请空间，在函数体中复制内容。**

``` c++
class Computer {
public:
	Computer(const char * brand, double price)
	: _brand(new char[strlen(brand) + 1]())
	, _price(price)
	{
        strcpy(_brand,brand);
    }
    
private:
	char * _brand;
	double _price;
};

void test0(){
    Computer pc("Apple",12000);
}
```

思考一下，以上代码有没有问题？

代码运行没有报错，但使用memcheck工具检查发现发生了内存泄漏。有new表达式被执行，就要想到通过delete表达式来进行回收。如果没有对应的回收机制，对象被销毁时，它所申请的堆空间不会被回收，就会发生内存泄漏。

那么如何进行妥善的内存回收呢？这需要交给**析构函数**来完成。



## 对象的销毁（析构函数）

1. 析构函数：对象在销毁时，一定会调用析构函数

2. 析构函数的作用：清理对象的数据成员申请的资源（堆空间）—— 析构函数并不负责清理数据成员（系统自动完成）

3. 形式：【特殊的成员函数】

   - **没有返回值**，即使是void也没有

   - **没有参数**
   - **函数名与类名相同**，在类名之前需要加上一个波浪号~ 

4. 析构函数只有一个（不能重载）

5. 析构函数默认情况下 ，系统也会自动提供一个

6. <span style=color:red;background:yellow>**当对象被销毁时 ，会自动调用析构函数【非常重要】**</span>



### 自定义析构函数

之前的例子中，我们没有显式定义出析构函数，但是没有问题，系统会自动提供一个默认的析构。

<span style=color:red;background:yellow>**析构函数作为一个清理数据成员申请的<u>堆空间</u>的接口存在。**</span>

当数据成员中有指针时，创建一个对象，会申请堆空间，销毁对象时默认析构不够用了（造成内存泄漏），此时就需要我们自定义析构函数。在析构函数中定义堆空间上内存回收的机制，就不会发生内存泄漏。

同样以 Computer 类为例

```` c++
class Computer {
public:
	Computer(const char * brand, double price)
	: _brand(new char[strlen(brand) + 1](/*可以写入"apple"写死，但不是我们想要的*/))
	, _price(price)
	{}
    // 析构函数是用来清理数据成员所申请的堆空间资源的
    // 默认的析构函数并不能实现这个实现这个功能
    // 需要我们定义析构函数的内容
    // 析构函数是提供给我们的接口，用来清理
	~Computer()
	{	
        if(_brand){
            delete [] _brand;
        	_brand = nullptr // 设为空指针，安全回收
        }
		cout << "~Computer()" << endl;
	}
private:
	char * _brand;
	double _price;
};
````



析构函数：如果指针成员申请了堆空间，就回收这片空间，并将指针成员设为空指针，进行安全回收。

<img src="./cppbase.assets/image-20230831155036140.png" alt="image-20230831155036140" style="zoom: 67%;" />



析构函数的规范写法为什么这样写呢？实际上，如果类中没有指针数据成员，即数据成员没有申请堆空间的情况下，默认的析构函数就够用了。

- 如果没有进行**安全回收**这一步会引发很多问题，此时我们没有学习类与对象的更多知识，可以做个简单小实验，看看会发生什么情况，思考一下原因

```C++
class Computer{
    ...
    ~Computer()
	{
        if(_brand){
            delete [] _brand;
            //_brand = nullptr; // 设为空指针，安全回收，注释之后double free
           }
        cout << "~Computer()" << endl;
	}
    ...
}

void test0(){
    Computer pc("apple",12000);
    pc.print();
    pc.~Computer(); // 手动调用析构函数
    // 之后还会自动调用一次析构，可能会造成double free的问题
}
```

 第一次手动调用析构函数时已经回收了这片堆空间，但是 **_brand 存的地址值依然能有效**，当对象销毁时自动调用析构函数，依然会进入if语句，再一次试图回收这篇空间，发生double free错误

- 如果没有对指针成员的判断，可能会有delete一个空指针的情况。尽管一些平台，delete本身会自动检查对象是否为空，如果为空就不做操作，但是在其他的一些平台这样做可能会导致风险，所以请按照规范去定义析构函数。 



注意：对象被销毁，一定会调用析构函数；<span style=color:red;background:yellow>**调用了析构函数，对象并不会被销毁。**</span >

上述例子中手动调用了析构函数，发现之后又自动调用了一次析构函数。

那么在手动调用析构函数之后，再次调用print函数,看看会发生什么？

``` c++
Computer pc("apple",12000);
pc.~Computer();
pc.print();
```

发现程序在print执行时，尝试对char型空指针进行输出，导致程序中断

```
// 程序遇到空指针是不允许去访问的
// 输出流运算符遇到char*会自动尝试去访问
char *p3 = nullptr;
cout << p3 << endl;
```

结论：<font color=red>**不建议手动调用析构函数，因为容易导致各种问题，应该让析构函数自动被调用。**</font>



### 构造函数和析构函数的调用时机（重点）

1. 对于**全局定义的对象**，每当程序开始运行，在**==主函数 main 接受程序控制权之前==**，就调用构造函数创建全局对象，**==整个程序结束时==**，自动调用全局对象的析构函数。
2. 对于**局部定义的对象**，每当==程序流程到达该**对象的定义处**==就调用构造函数，在**==程序离开局部对象的作用域==**时调用对象的析构函数。
3. 对于**关键字 `static` 定义的静态对象**，当==程序流程到达该**对象定义处**==调用构造函数，在**==整个程序结束时==**调用析构函数。
4. 对于用 **new 运算符创建的堆对象**，每当**==创建该对象时==**调用构造函数，在**==使用 delete 删除该对象时==**，调用析构函数。



## 本类型对象的复制

### 拷贝构造函数	`类名(const 类名 &)`

对于内置类型而言，使用一个变量初始化另一个变量是很常见的操作：

``` c++
int x = 1;
int y = x;
```

那么对于自定义类型，我们也希望能有这样的效果，如：

``` c++
Point pt1(1,2);
Point pt2 = pt1; // ok
pt2.print();
```

发现这种操作也是可以通过的。执行 `Point pt2 = pt1;` 语句时， pt1 对象已经存在，而 pt2 对象还不存在，所以也是这句创建了 pt2 对象，既然涉及到对象的创建，就必然需要调用构造函数，而这里会调用的就是拷贝构造函数(复制构造函数)。

#### 拷贝构造函数的定义

拷贝构造函数的形式是固定的：<span style=color:red;background:yellow>**类名(const 类名 &)  **</span>

1. 该函数是一个构造函数
2. 该函数**用一个已经存在的同类型的对象，来初始化新对象**，即对对象本身进行**复制**

没有显式定义拷贝构造函数，这条复制语句依然可以通过，说明编译器自动提供了默认的拷贝构造函数。其形式是：

``` c++
//编译器自动提供了默认的拷贝构造函数
class Computer{
    ...
    Point(const Point & rhs)
    : _ix(rhs._ix)
    , _iy(rhs._iy)
    {}
    ...
}
```

 拷贝构造函数看起来非常简单，那么我们尝试对Computer类的对象进行同样的复制操作。发现同样**可以编译通过，但运行报错==double free==**。思考一下为什么？

```C++
//可以编译通过，但运行报错double free
Computer pc("Acer",4500);
Computer pc2 = pc; // 调用拷贝构造函数	浅拷贝
```

<img src="./cppbase.assets/image-20230831161132618.png" alt="image-20230831161132618" style="zoom: 67%;" />

如果是默认的拷贝构造函数，pc2会对pc的_brand进行**==浅拷贝==**，指向同一片内存；pc2被销毁时，会调用析构函数，将这片堆空间进行回收；pc再销毁时，析构函数中又会试图回收这片空间，出现double free问题。

所以，如果拷贝构造函数需要显式写出时（该类有指针成员申请堆空间），在<u>自定义的拷贝构造函数中要换成**==深拷贝==**的方式</u>，**先申请空间，再复制内容**。

``` c++
//自定义拷贝构造函数 深拷贝
Computer::Computer(const Computer & rhs)
: _brand(new char[strlen(rhs._brand) + 1]())
, _price(rhs._price)
{
	strcpy(_brand, rhs._brand);
}
```

<img src="./cppbase.assets/image-20230831161420785.png" alt="image-20230831161420785" style="zoom: 67%;" />

#### 拷贝构造函数的调用时机（重点）

1. 当使**==用一个已经存在的对象初始化另一个同类型的新对象==**时；

   ```c++
   Computer pc("Acer",4500);
   Computer pc2 = pc; // 调用拷贝构造函数
   ```

2. 当函数参数（实参和形参的类型都是对象），**形参与实参结合时**（==**实参初始化形参**==）；

   —— 为了避免这次不必要的拷贝，可以使用引用作为参数

   ```c++
   void func(Computer rhs){ // 发生一次复制
       rhs.print();
   }
   void func2(Computer &rhs){ // 不会发生复制 调用拷贝构造函数
       rhs.print();
   }
   void test1(){
       Computer pc("apple",20000);
       // func(pc);
       func2(pc);
   }
   ```

3. 当**==函数的返回值是对象==**，执行return语句时（编译器有优化）。

   为了避免这次多余的拷贝，可以**使用引用作为返回值**，但一定要确保返回值的声明周期大于函数的生命周期

```c++
Computer func2(){ // 返回时会复制一次
	return Computer("ASUS",7000);
}

Computer pc3("Acer",5400);
Computer &func3(){ // 不会发生复制
	return pc3;
}

void test2(){
	// func2();
	func3();
}
```

第三种情况直接编译并不会显示拷贝构造函数的调用，但是底层实际是调用了的，加上去优化参数进行编译可以看到效果

```C++
g++ CopyComputer.cc -fno-elide-constructors
```



#### ==拷贝构造函数的形式探究*==

`类名(const 类名 &)`

<span style=color:red;background:yellow>**思考1：拷贝构造函数是否可以去掉引用符号（&）？**</span> `类名(const 类名)`

首先编译器不允许这样写，直接报错。

如果拷贝函数的参数中去掉引用符号，进行拷贝时调用拷贝构造函数的过程中会发生“**实参和形参都是对象，用实参初始化形参**”（拷贝构造第二种调用时机），会再一次调用拷贝构造函数。**==形成递归调用，直到栈溢出，导致程序崩溃。==**

![image-20240307215509533](./cppbase.assets/image-20240307215509533.png)

构造函数是最特殊的成员函数，不是对象来调用构造函数，而是编译器在看到创建对象的语句时，会自动生成一段代码，在这段代码中调用。



<span style=color:red;background:yellow>**思考2：拷贝构造函数是否可以去掉const？**</span> `类名(类名 &) 形式`

编译器不会报错

加const的第一个用意：为了确保==右操作数的数据成员**不被改变**==

加const的第二个用意：为了能够==复制临时对象的内容==（因为**非const引用不能绑定临时变量**（右值））

> 补充：左值与右值：
>
> ```c++
> /*左值*/
> int num = 1;
> &num; // 可以取地址的变量称之为左值
> 
> int &ref = num;
> const int &ref2 = num;
> 
> /*右值*/
> // &1; // 不能取地址的变量称之为右值	右值：临时变量、匿名变量、临时对象、匿名对象
> 
> // int &ref3 = 1; // error 非const引用不能绑定临时变量
> const int &ref4 = 1; // ok const引用绑定临时变量
> ```



###  赋值运算符函数	`类名& operator=(const 类名 &)`

赋值运算同样是一种很常见的运算，比如：

``` c++
int x = 1, y = 2;
x = y;
```

自定义类型当然也需要这种运算，比如：

``` c++
Point pt1(1, 2), pt2(3, 4);
pt1 = pt2; // 赋值操作
```

在执行 `pt1 = pt2;` 该语句时， pt1 与 pt2 都存在，所以不存在对象的构造，这要与 `Point pt2 = pt1;` 语句区分开，这是不同的。

#### 赋值运算符函数的形式

在上述例子中，当 = 作用于对象时，其实是把它当成一个函数来看待的。在执行 pt1 = pt2; 该语句时，需要调用的是<span style=color:red;background:yellow>**赋值运算符函数**</span>。其形式如下：

<span style=color:red;background:yellow>**类名& operator=(const 类名 &)**</span>

对Point类进行测试时，会发现我们不需要显式给出赋值运算符函数，就可以执行测试。这是因为如果类中没有显式定义赋值运算符函数时，**编译器会自动提供一个赋值运算符函数**。对于 Point 类而言，其实现如下:

``` c++
Point & Point::operator=(const Point & rhs){
	_ix = rhs._ix;
	_iy = rhs._iy;
}
```

手动写出赋值运算符，再加上函数调用的提示语句。执行发现语句被输出，也就是说，**当对象已经创建时，将另一个对象的内容复制给这个对象，会调用赋值运算符函数**。

那么现在又产生了问题

首先，赋值号是一个双目运算符，如果把它视为一个函数，那么应该有两个参数。但是从赋值运算符函数的形式上看只接收了一个参数，为什么？

其次，赋值运算符函数返回类型是`Point&`，那么它的返回值是什么？



这两个问题引出了一个重要的知识点——**this指针**

```c++
// 成员函数的参数列表首位会被编译器自动加上一个参数：this指针
// 作用：指向本对象，准确访问本对象的成员
// 形式：Point * const this
// 位置：成员函数参数列表首位，不能是显示写出
Point & operator=(const Point & rhs){
	this->_ix = rhs._ix;
	this->_iy = rhs._iy;
	cout << "Point & operator=(const Point & rhs)" << endl;
	return *this; // 返回本对象
}
```



#### this指针

- this指针的本质

this指针的本质是一个==**指针常量** `Type* const pointer;`== 它储存了<u>调用它的对象的地址</u>，不可被修改。这样成员函数才知道自己修改的成员变量是哪个对象的。

**this**是一个隐藏的指针，可以在类的成员函数中使用，它可以用来指向调用对象。当一个对象的成员函数被调用时，编译器会隐式地传递该对象的地址作为 this 指针。



- this指针存在哪儿

编译器在**生成程序时**加入了**获取对象首地址**的相关代码，将获取的首地址存放在了**寄存器**中，这就是this指针。



- this指针的生命周期

**this 指针的生命周期开始于<u>==成员函数==的执行开始</u>**。当一个非静态成员函数被调用时，this 指针被自动设置为指向调用该函数的对象实例。在成员函数执行期间，this 指针一直有效。它可以被用来访问调用对象的成员变量和成员函数。**this指针的生命周期结束于<u>==成员函数==的执行结束</u>**。当成员函数返回时，this指针的作用范围就结束了。



要注意，this指针的生命周期与它所指向的对象的生命周期虽然并不完全相同，但是是相关的。

this指针本身只在成员函数执行期间存在，但它指向的对象可能在成员函数执行前就已经存在，并且在成员函数执行后继续存在。

如果成员函数是通过一个已经销毁或未初始化的对象调用的，this指针将是悬挂的，它的使用将会是未定义行为。

```c++
void test1(){
    Point * p = new Point(1,2);
    delete p;
    p->print(); // 成员函数是通过一个已经销毁或未初始化的对象调用 未定义行为
    (*p).print();
}
```

理解以下问题：

1. 对象调用函数时，是如何找到自己本对象的数据成员的？    —— 通过this指针
2. this指针代表的是什么？                                                           —— 本对象
3. this指针在参数列表中的什么位置？                                        ——  参数列表的第一位（编译时编译器自动加上）
4. this指针的形式是什么？                                                           ——  `类名 * const this` （指针常量）

``` c++
Point & operator=(const Point & rhs){
    this->_ix = rhs._ix;
    this->_iy = rhs._iy;
    cout << "Point & operator=(const Point &)" << endl;
    return *this;
}
```

成员函数中可以加上this指针，展示本对象通过this指针找到本对象的数据成员。但是不要在参数列表中显式加上this指针，因为编译器一定会在参数列表的第一位加上this指针，如果显式再给一个，参数数量就不对了。



#### 赋值运算符函数的定义

注意：**如果对象的指针数据成员申请了堆空间**，默认的赋值运算符函数就不够用了，以Computer类为例，默认的赋值运算符函数长这样

``` c++
Computer & operator=(const Computer & rhs){
    this->_brand = rhs._brand; // 浅拷贝
    this->_price = rhs._price;
    return *this;
}
```

这里的指针成员_brand是进行的浅拷贝，会造成问题

![image-20240308195959228](./cppbase.assets/image-20240308195959228.png)



**思考：**

如果直接进行深拷贝，可行吗？存在内存泄漏，需要回收pc2._brand原本申请的空间

![image-20240308200010740](./cppbase.assets/image-20240308200010740.png)

```
Computer & operator=(const Computer & rhs){
    _brand = new char[strlen(rhs._brand)]();
    strcpy(_brand,rhs._brand);
    _price = rhs._price;
    return *this;
}
```

```
Computer pc("apple",1000);
Computer pc2("Xiaomi",100);
pc2 = pc; // 存在内存泄漏 "Xiaomi"
```



<span style=color:red;background:yellow>**总结步骤——四步走（重点）：**</span>

1. 考虑**自复制**问题 （例如：`pc=pc;`）
2. 回收左操作数原本申请的**堆空间**
3. **深拷贝**（以及其他的数据成员的复制）
4. 返回`*this`

``` c++
Computer & operator=(const Computer & rhs){
    if(this != &rhs){ // 1.考虑自复制
        delete [] _brand; // 2.回收左操作数原本申请的堆空间
        _brand = new char[strlen(rhs._brand)](); // 3.深拷贝（以及其他的数据成员的复制）
        strcpy(_brand,rhs._brand);
        _price = rhs._price;
    }
    return *this; // 4.返回*this
}
```



#### ==赋值运算符函数的形式探究*==

关于赋值运算符函数的形式探究也是面试中比较可能出现的问题，以下提出四个思考：

1. 赋值运算符函数的**返回必须是一个引用吗**？不加引用，返回的是复制，会发生一次拷贝构造

```C++
Computer operator=(const Computer & rhs){
    ...
    return *this;
}
```

2. 赋值操作符函数的**返回类型可以是void吗**？连续赋值出现问题，例：`pc=pc2=pc3;`

```C++
void operator=(const Computer & rhs){
    ...
}
```

3. 赋值操作符函数的**参数一定要是引用吗**？会调用一次多余的拷贝构造

```C++
Computer & operator=(const Computer rhs){
	...
	return *this;
}
```

4. 赋值操作符函数的**参数必须是一个const引用吗**？无法避免在赋值运算符函数中修改右操作数的内容，不合理

```C++
Computer & operator=(Computer & rhs){
	...
	return *this;
}
```



### ==三合成原则*==

**三合成原则**很容易在面试时被问到：

**==拷贝构造函数==、==赋值运算符函数==、==析构函数==，如果需要手动定义其中的一个，那么另外两个也需要手动定义。**



## 特殊的数据成员

在 C++ 的类中，有4种比较特殊的数据成员，分别是常量成员、引用成员、类对象成员和静态成员，它们的初始化与普通数据成员有所不同。

### 常量数据成员

当数据成员用 `const` 关键字进行修饰以后，就成为常量成员。一经初始化，该数据成员便具有“只读属性”，在程序中无法对其值修改。事实上，在构造函数体内对 const 数据成员赋值是非法的，==const数据成员需在**初始化列表**中进行初始化==，在函数体中无法再进行赋值（C++11之后也允许在声明时就初始化）

普通的 const 常量必须在声明时就初始化，初始化之后就不再允许修改值；

const 成员初始化后也不再允许修改值。

``` c++
class Point {
public:
	Point(int ix, int iy)
	: _ix(ix) // const int _ix = x;
	, _iy(iy)
	{
        // _ix = x; // error
        // _iy = y; // error
    }
private:
	const int _ix;
	const int _iy;
};
```



### 引用数据成员

引用数据成员在初始化列表中进行初始化，C++11之后允许在声明时初始化（绑定）。

之前的学习中，我们知道了引用要绑定到已经存在的变量，引用成员同样如此。

``` c++
class Point {
public:
    // Point(int x,int y,int z)
    // : _ix(x)  
    // , _iy(y)
    // , _iz(z)
    // {
    //     cout << "Point(int,int)" << endl;
    // }
	Point(int ix, int iy)
	: _ix(ix)
	, _iy(iy)
	, _iz(_ix) // int & _iz = _iz; 确保已绑定到已存在的变量
	{}
private:
	const int _ix;
	const int _iy;
	int & _iz;
};
```

思考：构造函数再接收一个参数，用这个参数初始化引用成员可以吗？

确保 _iz 绑定到已经存在的变量。

_iz 保定传入的 z，看起来虽然是确定的值，但是由于**值传递**会进行复制，所以实际上是去绑定一个**临时变量**，临时变量的生命周期只有当前行，等到绑定的时候就是不确定的值了。

_iz 绑定 _ix ,因为数据成员的初始化顺序与声明顺序一致，此时 _ix 已经完成了初始化，是一个确定的值，就没有问题。



### 对象成员

有时候，一个类对象会作为另一个类对象的数据成员被使用。比如一个直线类Line对象中包含两个 Point 对象。

==**对象成员<u>*必须*</u>在初始化列表中进行初始化**==。

注意：

1. 不能在声明对象成员时就去创建。
2. 初始化列表中写的是需要被初始化的对象成员的名称，而不是对象成员的类名。

``` c++
class Line {
public:
	Line(int x1, int y1, int x2, int y2)
	// 调用了Point的构造函数，不写参数列表，会自动调用Point默认无参构造
    : _pt1(x1, y1) // Point _pt1(x1,y1)
	, _pt2(x2, y2) // Point _pt2(x2,y2)
	{ cout << "Line(int,int,int,int)" << endl; }
    
    void printLine(){
        _pt1.print();
        cout << "---" << endl;
        _pt2.print();
    }
private:
    // _pt1 _pt2是Line类的对象成员
    // 生成Line类对象
    // 这个对象会包含两个Point类型的成员子对象
	Point _pt1;
	Point _pt2;
};
```

注意：如果在Line类的构造函数的初始化列表中没有显示的初始化Point类对象成员，编译器会自动去调用Point类型的默认无参构造；如果不想用Point的无参构造，那么必须在Line类的初始化列表中对Point类的数据成员进行初始化，不能在声明对象成员时直接进行初始化

```c++
private:
	// Point _pt1(1,2); // error
	Point _pt2;
```

此例子中，创建一个Line类的对象，会首先调用**Line的构造函数**，在此过程中(运行到初始化列表时)调用**Point的构造函数**完成Point类对象成员的初始化；Line对象销毁时会先调用**Line的析构函数**，析构函数执行完后，再调用**Point的析构函数**。

```
输出，看到的顺序，与真实顺序不同：
Point(int,int)
Point(int,int)
Line(int,int,int,int)
~Line()
~Point()
~Point()
```



### 静态数据成员（初始化在类外）

C++ 允许使用 static （静态存储）修饰数据成员，这样的成员**在编译时就被创建并初始化**的（与之相比，对象是在**运行时**被创建的），且其**实例只有一个，被所有该类的对象共享**，就像住在同一宿舍里的同学共享一个房间号一样。静态数据成员和之前介绍的静态变量一样，当程序执行时，该成员已经存在，一直到程序结束，任何该类对象都可对其进行访问，**静态数据成员存储在==全局/静态区==，并不占据对象的存储空间**。

<span style=color:red;background:yellow>**静态数据成员被整个类的所有对象共享。**</span>

``` c++
class Computer {
public:
	//...    
    
    double _price;
	static double _totalPrice;
private:
	char * _brand;
    // double _price;
    // //数据成员的类型前面加上static关键字
    // //表示这是一个static数据成员（共享）
	// static double _totalPrice;
};

double Computer::_totalPrice = 0; // 初始化 放在类外 全局变量

// static数据成员不依赖于特定的对象 所有的Computer类对象共享 访问时也可通过 类名::
void test(){
    cout << Computer::_totalPrice << endl;
}
```

静态成员规则：

1. **private**的静态数据成员**无法在类之外直接访问**（显然）
2. **对于==静态数据成员的初始化，必须放在类外==**（一般紧接着类的定义，这是规则1的特殊情况）
3. 静态数据成员初始化时不能在数据类型前面加static，在数据成员名前面要加上**==类名+作用域限定符==**
4. 如果有多条静态数据成员，那么它们的**==初始化顺序需要与声明顺序一致==**（规范）



## 特殊的成员函数

除了特殊的数据成员以外， C++ 类中还有两种特殊的成员函数：静态成员函数和 const 成员函数。我们先来看看静态成员函数。

### 静态成员函数	`static 返回值类型 func()`

在某一个成员函数的前面加上 `static` 关键字，这个函数就是静态成员函数。静态成员函数具有以下特点：

1. ==**静态成员函数不依赖于某一个对象；**==

2. 静态成员函数**==可以通过对象调用==**，但更常见的方式是**通过==类名加上作用域限定符==调用**；

3. 静态成员函数==**没有this指针**==；

4. **==静态成员函数无法直接访问非静态的成员==**，只能访问**静态成员变量/函数**（因为没有this指针）。
   - 注：但是==非静态的**成员函数**可以访问**静态成员函数/变量**==。


静态成员函数不能是构造函数/析构函数/赋值运算符函数/拷贝构造（因为这四个函数都会访问所有的数据成员，而 static 成员函数没有 this 指针）

``` c++
class Computer {
public:
	Computer(const char * brand, double price)
	: _brand(new char[strlen(brand) + 1]())
	, _price(price)
	{ _totalPrice += _price; }
   	//...
    
	//静态成员函数
	static void printTotalPrice()
	{
		cout << "totalPrice:" << _totalPrice << endl;
        // cout << _price << endl; // error
        // 在静态成员函数中没有this指针，找不到非静态的数据成员
        // 在静态成员函数中不能直接访问非静态成员函数
	}
    
    void p() //非静态的成员函数可以访问静态成员函数/变量
	{
		_totalPrice;
		printTotalPrice();
	}
    
private:
	char * _brand;
	double _price;
	static double _totalPrice;
};
double Computer::_totalPrice = 0;

void test(){
    Computer pc("Apple",20000);
    pc.printTotalPrice();//使用对象调用静态成员函数
    Computer::printTotalPrice();//类名::的方式调用静态函数，更为常用
}
```

想要完成Computer类的总价计算逻辑，除了构造函数之外，还需要做哪些补充呢？请结合前面学到的知识完成这个功能：无论是创建多个Computer对象，还是进行Computer对象的复制、赋值，Computer的总价始终能够正确输出。



### const成员函数	`返回值类型 func() const {}`

之前已经介绍了 const 的应用，实际上， const 在类成员函数中还有种特殊的用法。在成员函数的参数列表之后，函数执行体之前加上`const`关键字，这个函数就是const成员函数。

**形式：**`void func() const {}`

``` c++
class Computer{
public:
    //...
    void print() const
    {
        cout << "brand:" << _brand << endl;
        cout << "price:" << _price << endl;
    }
    //...
};
```

**特点：**

1. const成员函数中，**不能修改对象的数据成员**；
2. 当编译器发现该函数是const成员函数时，会自动**将this指针设置为==双重const限定==的指针**；



原本的this指针类型： `Point *const this`。
const成员函数的this指针： `const Point * const this`。前面一个const的作用是不能修改Point对象。



如果Point对象的成员变量为 `int _ix`  `int _iy`  `int * _pint`。对于 _pint，const 属性是施加在指针层面，也就是说不能修改这个指针，代表这不能修改这个指针的指向，但是并不能限制它修改指向的值。
指针数据成员 `const int * _pint` 的效果就是可以修改指向，不能修改指向的值。



## 对象的组织

有了自己定义的类，或者使用别人定义好的类创建对象，其机制与使用内置类型创建普通变量几乎完全一致，同样可以**创建 const 对象**、**创建指向对象的指针**、**创建对象数组**，还可**使用 new(delete) 来创建(回收)堆对象**。

### const对象	`const 类名 对象名(参数)`

类对象也可以声明为 const 对象，一般来说 ，<u>能作用于 const 对象的成员函数除了**构造函数**和**析构函数**，就只有 **==const 成员函数==**了</u>。因为 ==const 对象只能被**<u>创建</u>**、**<u>撤销</u>**和**<u>只读访问</u>**==，写操作是不允许的。

``` c++
const Point pt(1,2);
pt.print();
```

**const对象与const成员函数的规则：**

1. 当类中有const成员函数和非const成员函数重载时，const对象会调用const成员函数，非const对象会调用非const成员函数；
2. 当类中只有一个const成员函数时，无论const对象还是非const对象都可以调用这个版本；
3. 当类中只有一个非const成员函数时，<u>const对象就不能调用非const版本</u>。

**总结：**<span style=color:red;background:yellow>**如果一个成员函数中确定不会修改数据成员，就把它定义为const成员函数。**</span>



**思考1：**

一个类中可以有参数形式“完全相同”的两个成员函数（const版本与非const版本），既然没有报错重定义，那么它们必然是构成了重载，为什么它们能构成重载呢？**参数(this指针)不同**

```c++
//this指针变成了双重const限定的指针
void print() const
{
    // _ix = 1; //error const成员函数中不允许对数据成员进行修改
    cout << "(" << this->_ix 
        << "," << this->_iy
        << "," << (*_pint)
        << ")" << endl;
    cout << "print const" << endl;
    cout << endl;

    // _pint = new int(); // <========= error const成员函数中不能修改指针成员的指向
    *_pint = 100; // <========= 但是可以修改指针成员所指向的值

    cout << "(" << this->_ix 
        << "," << this->_iy
        << "," << (*_pint)
        << ")" << endl;
}

void print() 
{
    cout << "(" << this->_ix 
        << "," << this->_iy
        << "," << (*_pint)
        << ")" << endl;
    cout << "print" << endl;
    cout << endl;
    *_pint = 1999; // ok
    _pint = new int(); // ok
    cout << "(" << this->_ix 
        << "," << this->_iy
        << "," << (*_pint)
        << ")" << endl;
}

private:
	int _ix;
	int _iy;
    int * _pint;
```



**思考2：**

const成员函数中不允许修改数据成员，const数据成员初始化后不允许修改，其效果是否相同？请动手验证下面的问题

举例，如果有一个普通的指针成员，在const成员函数中，它被如何限制？

```c++
void print() const
{
    // _ix = 1; //error const成员函数中不允许对数据成员进行修改
    cout << "(" << this->_ix 
        << "," << this->_iy
        << "," << (*_pint)
        << ")" << endl;
    cout << "print const" << endl;
    cout << endl;

    // _pint = new int(); // error const成员函数中不能修改指针成员的指向
    *_pint = 100; // 但是可以修改指针成员所指向的值

    cout << "(" << this->_ix 
        << "," << this->_iy
        << "," << (*_pint)
        << ")" << endl;
}

private:
	int _ix;
	int _iy;
    int * _pint; // 普通指针成员
```

如果这个指针成员是一个const成员，初始化之后，在一个普通的成员函数里，它被如何限制？

```c++
void print() 
{
    cout << "(" << this->_ix 
        << "," << this->_iy
        << "," << (*_pint)
        << ")" << endl;
    cout << "print" << endl;
    cout << endl;
    // *_pint = 1999; // <========= error
    _pint = new int(); // <========= ok
    cout << "(" << this->_ix 
        << "," << this->_iy
        << "," << (*_pint)
        << ")" << endl;
}

private:
	int _ix;
	int _iy;
    const int * _pint; // <========= const成员
```



### 指向对象的指针	`类名 * 指针名 [=初始化表达式];`

对象占据一定的内存空间，和普通变量一致， C++ 程序中采用如下形式声明指向对象的指针：

`类名 * 指针名 [=初始化表达式];`

初始化表达式是可选的，既可以通过**取地址**（&对象名）给指针初始化，也可以通过**申请动态内存**给指针初始化，或者干脆不初始化（比如置为 nullptr ），在程序中再对该指针赋值。指针中存储的是对象所占内存空间的首地址。针对上述定义，则下列形式都是合法的：

``` c++
Point pt(1, 2);
// 指向对象的指针的三种初始化方式
Point * p1 = nullptr;
Point * p2 = &pt;
Point * p3 = new Point(3, 4);
```

问题：定义好这些指针后，如何利用指针去调用Point类的成员函数print？请试验一下

```c++
p2->print();
(*p2).print();
```



### 对象数组	`类名 对象数组名[个数]`

对象数组和标准类型数组的使用方法并没有什么不同，也有声明、初始化和使用等步骤。

- 对象数组的声明

``` c++
Point pts[2];
```

这种格式会自动调用默认构造函数或所有参数都是缺省值的构造函数。

- 对象数组的初始化（可以在声明时进行初始化）

``` c++
Point pts[2] = {Point(1, 2), Point(3, 4)};
Point pts[] = {Point(1, 2), Point(3, 4)};
Point pts[5] = {Point(1, 2), Point(3, 4)};
//或者
Point pts[2] = {{1, 2}, {3, 4}};
Point pts[] = {{1, 2}, {3, 4}};
Point pts[5] = {{1, 2}, {3, 4}};
```



### 堆对象	new/delete

和把一个简单变量创建在动态存储区一样，可以**用 new 和 delete 表达式为对象分配动态存储区**，在拷贝构造函数一节中已经介绍了为类内的指针成员分配动态内存的相关范例，这里主要讨论如何为对象和对象数组动态分配内存。如：

``` c++
void test()
{
	Point * pt1 = new Point(11, 12);
	pt1->print();
	delete pt1;
	pt1 = nullptr;
    
	Point * pt2 = new Point[5](); // 注意
	pt2->print();
	(pt2 + 1)->print();
	delete [] pt2;
    pt2 = nullptr;
}
```



## new/delete表达式的工作步骤

现在我们已经学习了new和delete的基本使用，在new/delete和malloc/free作对比时提到了二者的最本质区别
 —— new/delete是**表达式**，而malloc/free是**库函数**。

那么new/delete表达式的底层工作步骤是怎样的呢？我们有必要进行了解，因为很多时候写出的bug就藏在这个工作步骤中。



### new表达式的工作步骤

<span style=color:red;background:yellow>**使用new表达式时发生的三个步骤**</span>：

1. 调用**==operator new标准库函数==**申请未类型化的空间

2. 在该空间上调用该类型的**==构造函数==**初始化对象

3. 返回指向该对象的相应类型的指针



### delete表达式的工作步骤

<span style=color:red;background:yellow>**使用delete表达式时发生的两个步骤**</span>：

1. 调用**==析构函数==**,回收数据成员申请的资源(堆空间)

2. 调用**==operator delete库函数==**回收本对象所在的空间

   ``` c++
   //默认的operator new
   void * operator new(size_t sz){
       void * ret = malloc(sz);
   	return ret;
   }
   
   //默认的operator delete
   void operator delete(void * p){
       free(p);
   }
   ```

   通过一个例子来认识这两个函数的用法

   ``` c++
   class Student
   {
   public:
   	Student(int id, const char * name)
   	: _id(id)
   	, _name(new char[strlen(name) + 1]())
   	{
   		strcpy(_name, name);
   		cout << "Student()" << endl;
   	}
       
   	~Student()
   	{
   		delete [] _name;
   		cout << "~Student()" << endl;
   	}
       
   	void * operator new(size_t sz)
   	{
           cout << "operator new" << endl;
   		void * ret = malloc(sz);
   		return ret;
   	}
       
   	void operator delete(void * pointer)
   	{
           cout << "operator delete" << endl;
   		free(pointer);
   	}
       
   	void print() const
   	{
   		cout << "id:" << _id << endl
   			<< "name:" << _name << endl;
   	}
   private:
   	int _id;
   	char * _name;
   };
   
   void test0()
   {
   	Student * stu = new Student(100, "Jackie");
   	stu->print();
       delete stu;
   }
   ```



<img src="./cppbase.assets/image-20240308220829925.png" alt="image-20240308220829925" style="zoom:50%;" />



### 创建对象的探究

<u>定义一个类，即使什么成员函数也不定义，依然可以创建**栈对象**和**堆对象**</u>。之前我们知道了构造函数和析构函数会自动提供默认版本，那么能够创建堆对象、回收堆对象，说明会自动提供默认的operator new / operator delete函数。

默认的operator new / operator delete函数实际上就是通过malloc / free 实现的申请 / 回收堆空间。



请探究：

- **创建<u>==堆对象==</u>需要什么条件？**

思路：将创建、销毁对象过程中所调用到的函数一一设为私有，私有的成员函数**==在类外就无法被直接调用了==**。

需要合法的<u>`operator new`、`operator delete`、**构造函数**</u>，对析构函数没有要求，在销毁堆对象的时候才会调用析构函数。

- **创建<u>==栈对象==</u>需要什么条件？**

需要合法的<u>**构造函数**、**析构函数**</u>，对`operator new`、`operator delete`没有要求。



**根据探究得出的结论，想要实现以下需求，应该怎么做？**

- **只能生成栈对象 , 不能生成堆对象**

可以将`operater new()`、`operater delete()`设为私有。

栈对象的创建和销毁是在编译时确定的，由编译器自动插入的调用代码来管理。栈对象的内存是在栈上分配的，而不是通过operator new在堆上分配的。

然而，将类的成员operator new和operator delete设置为私有确实会阻止外部代码通过new关键字来动态分配该类的对象。这是因为new关键字在内部会调用类的operator new函数来分配内存。如果你将这个函数设置为私有的，外部代码将无法直接调用它，因此无法创建堆对象。

- **只能生成堆对象 ，不能生成栈对象**

 可以将析构函数设为私有

栈对象会在它们的作用域结束时**自动调用析构函数**，但由于析构函数是私有的，栈对象无法调用它，因此编译器会报错。

但是，即使析构函数是私有的，仍然可以创建堆对象。堆对象是通过使用new关键字在堆上动态分配内存创建的，它们不会在作用域结束时自动调用析构函数。因此，即使析构函数是私有的，也可以创建堆对象。但是，由于堆对象无法调用私有的析构函数，这会导致**内存泄漏**，因为无法释放堆对象所占用的内存。因此，将析构函数设置为私有通常不是一个好主意，除非有特殊的原因和相应的机制来确保对象的正确析构。



## 单例模式（==重点*==）

单例模式是23种常用设计模式中最简单的设计模式之一，它**提供了一种创建对象的方式，确保只有单个对象被创建**。这个设计模式主要目的是想在==整个系统中只能出现类的一个实例，即一个类只有一个对象==。

### 将单例对象创建在==静态区==（不好：有内存压力）

根据已经学过的知识进行分析：

1. 将==**构造函数**私有==，确保在类外无法调用；
2. ==通过**静态成员函数`getInstance()`**创建局部静态对象==，确保对象的生命周期和唯一性；

3. ==`getInstance()`的返回值设为**引用**==，避免复制；

<font color=red>**隐患：如果单例对象所占空间较大，可能会对静态区造成内存压力。**</font>

``` c++
class Point
{
public:
    static Point & getInstance() {
        // 当静态函数多次被调用
        // 静态的局部对象只会被初始化一次
        // 第一次调用时，静态对象会被初始化为一个对象实例
        // 后续的调用中，静态局部对象已经存在，不会再初始化，而是直接返回已经初始化的对象实例
        static Point pt(1,2);
        return pt;
    }
    
    void print() const {
        cout << "(" << this->_ix
             << "," << this->_iy
             << ")" << endl;
    }

private:
    Point(int x,int y)
    : _ix(x)
    , _iy(y)
    {
        cout << "Point(int,int)" << endl;
    }
private:
    int _ix;
    int _iy;
};

void test0(){
    Point & pt = Point::getInstance();
    pt.print();

    Point & pt2 = Point::getInstance();
    pt2.print();

    cout << &pt << endl;
    cout << &pt2 << endl;
}
```



### 将单例对象创建在==堆区==（合理）

既然将单例对象创建在全局/静态区可能会有内存压力，那么为这个单例对象动态分配空间是比较合理的选择。

分析：

1. ==**构造函数**私有==；

2. ==通过**静态成员函数`getInstance()`**创建堆上的对象==；

3. ==返回 Point* 类型的**指针**==；

4. ==通过静态成员函数完成堆对象的**回收**==。

   ```c++
   ...
       
   public:
       static Point * getInstance(){
           if(_pInstance == nullptr){ // ===========注意============
               _pInstance = new Point(1,2);
           }
           return _pInstance;
       }
   
       static void destroy(){
           if(_pInstance){
               delete _pInstance;
               _pInstance = nullptr;
           }
       }
   
   ...
       
   private:
   	int _ix;
   	int _iy;
       static Point * _pInstance; //===========注意============
   };
   
   Point * Point::_pInstance = nullptr; //===========注意============
   
   void test0()
   {
       Point * pt = Point::getInstance();
       Point * pt2 = Point::getInstance();
       cout << pt << endl;
       cout << pt2 << endl;
       pt->destroy();
       pt2->print();
       
       //单例模式的规范
       Point::getInstance()->print();
       Point::getInstance()->destroy();
       // 或 Point::destroy();
   }
   ```

   **单例模式的规范**：不创建这些指针来接，每次只使用 _pInstance 来访问。

   <img src="./cppbase.assets/image-20240309090954316.png" alt="image-20240309090954316" style="zoom:67%;" />



### 单例对象的数据成员申请堆空间

要求：实现一个单例的 Computer 类，包含品牌和价格信息。

```c++
#include <string.h>
#include <iostream>
using std::cout;
using std::endl;

class Computer{
public:
    static Computer * getInstance() {
        if(_pInstance == nullptr) {
            _pInstance = new Computer("apple",20000);
        }
        return _pInstance;
    }

    void init(const char * brand, double price) {
        if(_brand) { // 如果已经指向了一片堆空间，先回收
            delete [] _brand;
            _brand = nullptr;
        }
        _brand = new char[strlen(brand) + 1](); // 再重新申请
        strcpy(_brand,brand);
        _price = price;
        cout<< "init:" << endl;
        this->print();
    }

    static void destroy() {
        if(_pInstance) {
            delete _pInstance;
            _pInstance = nullptr;
        }
        cout << "heap delete" << endl;
    }

    void print() {
        cout << "brand:" << _brand << endl;
        cout << "price:" << _price << endl;
    }

private:
    Computer() = default;
    Computer(const char * brand,double price)
    : _brand(new char[strlen(brand) + 1]())
    , _price(price) // _price = price;
    {
        cout << "Computer(const char *,double)" << endl;
        strcpy(_brand,brand);
    }

    ~Computer() {
        if(_brand) {
            delete [] _brand;
            _brand = nullptr;
        }
        cout << "~Computer()" << endl;
    }

    Computer(const Computer & rhs) = delete;
    Computer & operator=(const Computer & rhs) = delete;
    
private:
    char * _brand;
    double _price;
    static Computer * _pInstance;
};
Computer * Computer::_pInstance = nullptr;

void test0(){
    Computer::getInstance()->print();
    Computer::getInstance()->init("iphone",10000);
    Computer::getInstance()->init("mi",10000);
    Computer::destroy();
}

int main(void){
    test0();
    return 0;
}
```



### 单例模式的应用场景

1、有**==频繁实例化然后销毁==**的情况，也就是频繁的 new 对象，可以考虑单例模式；

2、**==创建对象时耗时过多或者耗资源过多==**，但又经常用到的对象；

3、当**==某个资源需要在整个程序中只有一个实例==**时，可以使用单例模式进行管理（全局资源管理）。例如数据库连接池、日志记录器等；

4、当需要**==读取和管理程序配置文件==**时，可以使用单例模式确保只有一个实例来管理配置文件的读取和写入操作（配置文件管理）；

5、在**==多线程编程==**中，线程池是一种常见的设计模式。使用单例模式可以确保只有一个线程池实例，方便管理和控制线程的创建和销毁；

6、**==GUI应用程序中的全局状态管理==**：在GUI应用程序中，可能需要管理一些全局状态，例如用户信息、应用程序配置等。使用单例模式可以确保全局状态的唯一性和一致性。



## C++字符串

有了类与对象的知识基础之后，我们可以来认识一下应用广泛的两种对象——C++字符串、C++动态数组。

先来看看C++字符串：

字符串处理在程序中应用广泛，**==C 风格字符串是以 '\0' （空字符）来结尾的字符数组==**，在C++中通常用 `const char *` 表示，用 `""` 包括的认为是C风格字符串。

对字符串进行操作的 C 函数定义在头文件 `<string.h>` 或 `<cstring>` 中。常用的库函数如下：

``` c++
//字符检查函数(非修改式操作)
size_t strlen(const char *str);//返回str的长度，不包括null结束符

//比较lhs和rhs是否相同。lhs等于rhs,返回0; lhs大于rhs，返回正数; lhs小于rhs，返回负数
int strcmp(const char *lhs, const char *rhs);
int strncmp(const char *lhs, const char *rhs, size_t count);

//在str中查找首次出现ch字符的位置；查找不到，返回空指针
char *strchr(const char *str, int ch);

//在str中查找首次出现子串substr的位置；查找不到，返回空指针
char *strstr(const char* str, const char* substr);

//字符控制函数(修改式操作)
char *strcpy(char *dest, const char *src);//将src复制给dest，返回dest
char *strncpy(char *dest, const char *src, size_t count);
char *strcat(char *dest, const char *src);//连接两个字符串
char *strncat(char *dest, const char *src, size_t count);
```

在使用时，程序员需要考虑字符数组大小的开辟，结尾空字符的处理，使用起来有诸多不便。

``` c
void test0()
{
	char str[] = "hello";
	char * pstr = "world";
	//求取字符串长度
	printf("%d\n", strlen(str));
    
	//字符串拼接
	char * ptmp = (char*)malloc(strlen(str) + strlen(pstr) + 1);
	strcpy(ptmp, str);
	strcat(ptmp, pstr);
	printf("%s\n", ptmp);
    
	//查找子串
	char * p1 = strstr(ptmp, "world");
	free(ptmp);
}
```



### C++风格字符串

C++ 提供了 `std::string` （后面简写为 string ）类用于字符串的处理。 string 类定义在 C++ 头文件`<string> `中，注意和头文件 `<cstring> `区分， `<cstring>` 其实是对 C 标准库中的 `<string.h> ` 的封装，其定义的是一些对 C 风格字符串的处理函数。`<string>`已经包含在了 `<iostream>` 中。

尽管 C++ 支持 C 风格字符串，但在 C++ 程序中最好还是不要使用它们。这是因为 C 风格字符串不仅使用起来不太方便，而且极易引发程序漏洞，是诸多安全问题的根本原因。与 C 风格字符串相比， string 不必担心内存是否足够、字符串长度，结尾的空白符等等。 string 作为一个类出现，其集成的成员操作函数功能强大，几乎能满足所有的需求。从另一个角度上说，**==完全可以把 string 当成是 C++ 的内置数据类型，放在和 int 、 double 等内置类型同等位置上==**。

`std::string`标准库提供的一个自定义类类型`basic_string`，string 类本质上其实是 `basic_string` 类模板关于 char 型的实例化。使用起来不需要关心内存，直接使用即可。

### string的构造

basic_string的常用构造——查看C++参考文档（cppreference-zh-20211231.chm）

```C++
basic_string(); //无参构造

basic_string( size_type count,
              CharT ch，
              const Allocator& alloc = Allocator() ); // count + 字符

basic_string( const basic_string& other,
              size_type pos,
              size_type count,
              const Allocator& alloc = Allocator() ); // 接收一个basic_string对象

basic_string( const CharT* s,
              size_type count,
              const Allocator& alloc = Allocator() ); // 接收一个C风格字符串
```

`basic_string`是一个模板类，它是`std::string`的基类。这里涉及到后面继承与模板的知识，现在我们掌握使用方法即可。

在创建字符串对象时，我们可以直接使用`std::string`作为类名，如`std::string str = "hello" `。这是因为C++标准库已经为我们定义了`std::string`这个类型的别名。

C++中用 `const char*` 表示c风格的字符串



**==string对象==常用的构造**

``` c++
string(); // 默认构造函数，生成一个空字符串
string(const char * rhs); // 通过c风格字符串构造一个string对象
string(const char * rhs, size_type count); // 通过rhs的前count个字符构造一个string对象
string(const string & rhs); // 复制拷贝构造函数
string(size_type count, char ch); // 生成一个string对象，该对象包含count个ch字符
string(InputIt first, InputIt last); // 以区间[first, last)内的字符创建一个string对象
```

还可以用拼接的方式构造string

原理：`basic_string`对加法运算符进行了默认重载（后续会学到），其本质是通过+号进行计算后得到一个`basic_string`对象，再用这个对象去创建新的`basic_string`对象

``` c++
//采取拼接的方式创建字符串
//可以拼接string、字符、C风格字符串
string str3 = str1 + str2;
string str4 = str2 + ',' + str3;
string str5 = str2 + ",world!";
```



### string的常用操作(成员函数)

```c++
const CharT * data() const;
const CHarT * c_str() const; // C++字符串转为C字符串

bool empty() const; // 判空

size_type size() const; // 获取字符数
size_type length() const;

void push_back(CharT ch); // 字符串结尾追加字符

// 在字符串的末尾添加内容，返回修改后的字符串
basic_string & append(size_type count, CharT ch); // 添加count个字符
basic_string & append(const basic_string & str); // 添加字符串
basic_string & append(const basic_string & str, size_type pos, size_type count); // 从原字符串的pos位置，添加字符串的count个字符
basic_string & append(const charT * s); // 添加C风格字符串

// 查找子串
size_type find(const basic_string & str, size_type pos = 0) const;
size_type find(CharT ch, size_type pos = 0) const;
size_type find(const CharT * s, size_type pos, size_type count) const;
```

实践一下string的各种操作，体会C++字符串的遍历。

找一个不存在的字符串，返回一个**64位系统地址的最大值**

```
void test1(){
    string str("hello");
    //pos是从0开始的
    size_t pos = str.find('e');
    cout << pos << endl; // 1

    string str2("lly");
    cout << str.find(str2) << endl; // 18446744073709551615
}
```



==**补充**==：两个basic_string字符串比较，可以直接使用 == 等符号进行判断

原理：basic_string对==运算符进行了**默认重载**（后续会学到）

``` c++
//非成员函数
bool operator==(const string & lhs, const string & rhs);
bool operator!=(const string & lhs, const string & rhs);
bool operator>(const string & lhs, const string & rhs);
bool operator<(const string & lhs, const string & rhs);
bool operator>=(const string & lhs, const string & rhs);
bool operator<=(const string & lhs, const string & rhs);
```



### string的遍历（重点）

string实际上也可以看作是一种**==存储char型数据的容器==**，对string的遍历方法是之后对各种容器遍历的一个铺垫。

（1）**通过下标遍历**

string 对象可以使用下标操作符[]进行访问。

``` c++
//使用下标遍历
for(size_t idx = 0; idx < str.size(); ++idx){
    cout << str[idx] << " ";
}
cout << endl;
```

需要注意的是操作符[]并不检查索引是否有效，如果索引超出范围，会引起未定义的行为。而函数 at() 会检查，如果使用 at()的时候索引无效，会抛出 out_of_range 异常

``` c++
string str("hello");
cout << str.at(4) << endl;  //输出o
cout << str.at(5) << endl;  //运行时抛出异常
```



（2）**增强for循环遍历**

针对容器，可以使用增强for循环进行遍历其中的元素。增强for循环经常和`auto`关键字一起使用，`auto`关键字可以**自动推导类型**。

``` c++
// 增强for循环通常与auto关键字在一起使用，auto能够自动推导出类型
// & 表示引用，直接操作元素本身
for(auto & ch : str){  //只要是str中的元素，就一一遍历
    cout << ch << " ";
}
cout << endl;
```



（3）**迭代器方式进行遍历**

**==string字符串==**利用连续空间存储字符，所以可以利用地址遍历。这里我们提出一个概念——迭代器。迭代器可以理解为是**广义的指针**。它可以像指针一样进行解引用、移位等操作。迭代器是容器用来访问元素的重要手段，容器都有相应的函数来获取特定的迭代器（此处可以简单理解为指向特定元素的指针）。在STL的阶段，我们会对迭代器进行更详细的讲解，现在我们只需要掌握它的基本使用即可。

**begin函数返回首迭代器（指向==首个元素==的指针）**；

**end函数返回尾后迭代器（指向最后一个元素的==后一位== '\0' 的指针）**。

![image-20240229161242290](./cppbase.assets/image-20240229161242290.png)

如指针一样，迭代器也有其固定的形式。

``` c++
//某容器的迭代器形式为 容器名::iterator
//此处auto推导出来it的类型为string::iterator
auto it = str.begin();
while(it != str.end()){
    cout << *it << " ";
	++it;
}
cout << endl;
```



## C++动态数组

C++中，std::vector（向量）是一个动态数组容器，能存放任意类型的数据。

其动态性体现在以下几个方面：

1. 动态大小：`std::vector` 可以根据需要自动调整自身的大小。它在内部管理一个动态分配的数组，可以根据元素的数量进行自动扩容或缩减。当元素数量超过当前容量时，`std::vector` 会重新分配内存，并将元素复制到新的内存位置。这使得 `std::vector` 能够根据需要动态地增长或缩小容量，而无需手动管理内存。
2. 动态插入和删除：`std::vector` 允许在任意位置插入或删除元素，而不会影响其他元素的位置。当插入新元素时，`std::vector` 会自动调整容量，并将后续元素向后移动以腾出空间。同样地，当删除元素时，`std::vector` 会自动调整容量，并将后续元素向前移动以填补空缺。
3. 动态访问：`std::vector` 提供了随机访问元素的能力。可以通过索引直接访问容器中的元素，而不需要遍历整个容器。这使得对元素的访问具有常数时间复杂度（O(1)），无论容器的大小如何。



### vector的构造

vector常用的几种构造形式：

（1）无参构造，仅指明vector存放元素的种类，没有存放元素；

``` c++
vector <int> numbers;
```



（2）传入一个参数，指明vector存放元素的种类和数量，参数是存放元素的数量，每个元素的值为该类型对应的默认值；

``` c++
vector<long> numbers2(10); // 存放10个0
```



（3）传入两个参数，第一个参数为vetor存放元素的数量，第二个参数为每个元素的值（相同）；

``` c++
vector<long> numbers2(10，20); // 存放10个20
```



（4）通过列表初始化vector，直接指明存放的所有元素的值

``` c++
vector<int> number3{1,2,3,4,5,6,7}; // 存放1 2 3 4 5 6 7
```



（5）迭代器方式初始化vector，传入两个迭代器作为参数，第一个为首迭代器，第二个为尾后迭代器；

``` c++
vector<int> number3{1,2,3,4,5,6,7};
vector<int> number4(number3.begin(),number3.end() - 3); // 推测一下，number4中存了哪些元素
// 1 2 3 4 左闭右开的区间
vector<long> number5(&number3[0],&number3[5]); // 1 2 3 4  参数：首迭代器 尾后迭代器
```



### vector的常用操作

```C++
iterator begin(); // 返回首位迭代器
iterator end(); // 返回尾后迭代器

bool empty() const; // 判空

size_type size() const; // 返回容器中存放的元素个数
size_type capacity() const; // 返回容器容量（最多可以存放元素的个数）

void push_back(const T & value); // 在最后一个元素的后面再存放元素

void pop_back(); // 删除最后一个元素
void clear(); // 清空所有元素，但不释放空间
void shrink_to_fit(); // 释放多余的空间（可以存放元素，但没有存放元素的空间）

void reserve(size_type new_cap); // 申请空间，不存放元素
// reserve可以与无参构造一起使用，如果确定了大概需要存放多少个元素，可以使用reserve去申请空间，但是不会存放元素，对比有参的构造，能够节省空间
```



**vector支持动态扩容：**

gcc上每次扩容变为原本的二倍，开辟一个原本二倍的地址空间，复制，再回收原本的空间

visio studio上面是1.5倍扩容

——很多技术上的具体实现，在不同的平台上细节不容，c++标准给出功能的要求，各个编辑器只需要实现此功能，具体细节可以不一样



**vector不仅能够存放内置类型变量，也能存放自定义类型对象和其他容器**

试着完成一下：

- vector中存放自定义类型Test的对象并遍历

```

```



- vector中存放string并遍历

```

```



- vector中存放vector

```

```



### vector的动态扩容

当**vector存放满后**，仍然追加存放元素，vector会进行==自动扩容==。

```` c++
vector<int> numbers;
cout << numbers.size() << endl;
cout << numbers.capacity() << endl;

numbers.push_back(1);
cout << numbers.size() << endl;
cout << numbers.capacity() << endl;

numbers.push_back(1);
cout << numbers.size() << endl;
cout << numbers.capacity() << endl;

numbers.push_back(1);
cout << numbers.size() << endl;
cout << numbers.capacity() << endl;
//...
````

多追加一些元素，看看元素数量和容器容量的关系，思考一下 vector 的容量是如何增长的呢？



> 常见面试题：
>
> **==vector 的容量工作步骤如下*==**：
>
> 1. 开辟空间
>
> 2. Allocator 分配（后面STL阶段学习）
>
> 3. 复制，再添加新元素
>
> 4. 回收原空间
>
> 5. 注意==迭代器会失效，需要重新置位==
>
> ![image-20231009155105294](./cppbase.assets/image-20231009155105294.png)

可以在VS上试试看上述代码的结果



### ==vector的底层实现（重点*）==

利用`sizeof`查看vector对象的大小时，发现无论存放的元素类型、数量如何，其大小始终为**==24个字节==**（64位环境）

因为vector对象是由三个指针组成，==**vector对象**在**栈区**，vector的**数据**在**堆区**==。

<img src="./cppbase.assets/image-20231009161203935.png" alt="image-20231009161203935" style="zoom:80%;" />

<span style=color:red;background:yellow>**_start指向当前数组中第一个元素存放的位置**</span>

<span style=color:red;background:yellow>**_finish指向当前数组中最后一个元素存放的下一个位置**</span>

<span style=color:red;background:yellow>**_end_of_storage指向当前数组能够存放元素的最后一个空间的下一个位置**</span>



可以推导出

```
size():  _finish - _start
capacity():  _end_of_storage - start
```



# 第三章 C++输入输出流

- **输入输出的含义**

以前所用到的输入和输出，都是以终端为对象的，即从键盘输入数据，运行结果输出到显示器屏幕上。从操作系统的角度看，每一个与主机相连的输入输出设备都被看作一个**文件**。除了以终端为对象进行输入和输出外，还经常用磁盘(光盘)作为输入输出对象，磁盘文件既可以作为**输入文件**，也可以作为**输出文件**。

在编程语言中的输入输出含义有所不同。**程序的输入**指的是从输入文件将数据传送给程序(内存)，**程序的输出**指的是从程序(内存)将数据传送给输出文件。



- **C++输入输出流机制**

C++ 的 I/O 发生在流中，流是字节序列。如果字节流是从设备（如键盘、磁盘驱动器、网络连接等）流向内存，这叫做输入操作。如果字节流是从内存流向设备（如显示屏、打印机、磁盘驱动器、网络连接等），这叫做输出操作。

就 C++ 程序而言， I/O 操作可以简单地看作是从程序移进或移出字节，程序只需要关心是否正确地输出了字节数据，以及是否正确地输入了要读取字节数据，特定 I/O 设备的细节对程序员是隐藏的。



- **C++常用流类型**

C++ 的输入与输出包括以下3方面的内容: 

（1） **对系统指定的标准设备的输入和输出**。即从键盘输入数据，输出到显示器屏幕。这种输入输出称为标准的输入输出，简称**标准** I/O 。

（2） **以外存磁盘文件为对象进行输入和输出**，即从磁盘文件输入数据，数据输出到磁盘文件。以外存文件为对象的输入输出称为文件的输入输出，简称**文件** I/O 。

（3） **对内存中指定的空间进行输入和输出**。通常指定一个字符数组作为存储空间（实际上可以利用该空间存储任何信息）。这种输入和输出称为<u>字符串输入输出</u>，简称**串** I/O 。



**常用的输入输出流如下：**

| 类名          | 作用             | 头文件   |
| ------------- | ---------------- | -------- |
| istream       | 通用输入流       | iostream |
| ostream       | 通用输出流       | iostream |
| iostream      | 通用输入输出流   | iostream |
| ifstream      | 文件输入流       | fstream  |
| oftream       | 文件输出流       | fstream  |
| fstream       | 文件输入输出流   | fstream  |
| istringstream | 字符串输入流     | sstream  |
| ostringstream | 字符串输出流     | sstream  |
| stringstream  | 字符串输入输出流 | sstream  |

![image-20240312194548078](./cppbase.assets/image-20240312194548078.png)



## 流的四种状态（重点）

IO 操作与生俱来的一个问题是可能会发生错误，一些错误是可以恢复的，另一些是不可以的。在C++ 标准库中，用 iostate 来表示流的状态，不同的编译器 iostate 的实现可能不一样，不过都有四种状态：

- <span style=color:red;background:yellow>**badbit **</span>表示发生**==系统级的错误==**，如不可恢复的读写错误。通常情况下一旦 badbit 被置位，流就无法再使用了。

- <span style=color:red;background:yellow>**failbit **</span>表示发生**==可恢复的错误==**，如期望读取一个数值，却读出一个字符等错误。这种问题通常是可以修改的，流还可以继续使用。

- <span style=color:red;background:yellow>**eofbit**</span>表示**==到达流结尾位置==**， 此时eofbit 和 failbit 都会被置位。

- <span style=color:red;background:yellow>**goodbit **</span>表示==流处于**有效状态**==。流在有效状态下，才能正常使用。如果 badbit 、 failbit 和 eofbit 任何一个被置位，则流无法正常使用。

这四种状态都定义在类 ios_base 中，作为其数据成员存在。在 GNU GCC7.4 的源码中，流状态的实现

```
cd /usr/include/c++/7/bits
vim ios_base.h
```

如下：

![image-20240302122820267](./cppbase.assets/image-20240302122820267.png)

通过流的状态函数实现

```C++
bool good() const      // 流是goodbit状态，返回true，否则返回false
bool bad() const       // 流是badbit状态，返回true，否则返回false
bool fail() const      // 流是failbit状态，返回true，否则返回false
bool eof() const       // 流是eofbit状态，返回true，否则返回false
```



## 标准输入输出流	`iostream`

对系统指定的标准设备的输入和输出。即从键盘输入数据，输出到显示器屏幕。这种输入输出称为标准输入输出，简称**标准** I/O

C++标准库定义了三个预定义的标准输入输出流对象，分别是 `std::cin`、`std::cout` 和 `std::cerr`。它们分别对应于标准输入设备（通常是键盘）、标准输出设备（通常是显示器）和标准错误设备（通常是显示器）。

标准输入、输出的内容包含在头文件iostream中。

有时候会看到通用输入输出流的说法，这是一个更广泛的概念，可以与各种类型的输入输出设备进行交互，包括标准输入输出设备、文件、网络等。

### 标准输入流	`istream`

istream 类定义了一个全局输入流对象，即 ==cin== , 代表的是**标准输入**，它从标准输入设备(键盘)获取数据，程序中的变量通过==流提取符 “>>”（输入流符号）== 从流中提取数据。

流提取符 “>>” 从流中提取数据时通常<u>跳过输入流中的**空格**、 **tab 键**、**换行符**等空白字符。只有在输入完数据再按**==回车键==**后，该行数据才被送入键盘<font color=red>**缓冲区**</font>，形成输入流，提取运算符 “>>” 才能从中提取数据</u>。需要注意保证从流中读取数据能正常进行。（流的缓冲机制在下一节中学习）

下面来看一个例子，每次从 cin 中获取一个字符:

``` c++
void printStreamStatus(std::istream & is){ // cin无法复制 要加&不然放入cin报错
    cout << "is's goodbit:" << is.good() << endl;
    cout << "is's badbit:" << is.bad() << endl;
    cout << "is's failbit:" << is.fail() << endl;
    cout << "is's eofbit:" << is.eof() << endl;
}

void test0(){
    printStreamStatus(cin);  //goodbit状态
    int num = 0;    
    cin >> num;   
    cout << "num:" << num << endl;
    printStreamStatus(cin);  //进行一次输入后再检查cin的状态
}
```

如果没有进行正确的输入，输入流会进入failbit的状态，无法正常工作，需要恢复流的状态。

- 查看C++参考文档，需要利用==**clear**（恢复流的状态）==和==**ignore**（清空缓冲区）==函数配合，实现这个过程


``` c++
    if(!cin.good()){
        //恢复流的状态
        cin.clear();
        //清空缓冲区，才能继续使用
        cin.ignore(std::numeric_limits<std::streamsize>::max(),'\n');
        cout << endl;
        printStreamStatus(cin);
    }
```

思考，如何完成一个输入整型数据的实现（如果是非法输入则继续要求输入）

``` c++
void test(int num){
    cout << "请输入一个int型数据" << endl;
    // 逗号表达式整体的值为最后一个逗号之后的表达式的值
    while(cin >> num, !cin.eof()){
        if(cin.bad()){
            cout << "cin has broken!" << endl;
            return;
        }else if(cin.fail()){
            cin.clear();
            cin.ignore((std::numeric_limits<std::streamsize>::max(),'\n');
            cout << "请输入一个int型数据" << endl;
        }else{
            // 输入是合法的
            cout << "num:" << num << endl;
            break;
        }
    }
}
int main(){
    int num = 0;
    test(num);
    return 0;
}
```

```c++
void test1(){
    int number = 10;
    int number2 = 20;
    // 或 if(cin.good()){
    if(cin){
        cout << "hello" << endl;
    }
    // cin >> number这个表达式的返回值就是cin
    // cin对象是good状态时就可以视为true,还可以继续正常工作
    cin >> number >> number2;
    cout << number << endl;
    cout << number2 << endl;
}
```



### 缓冲机制

在标准输入输出流的测试中发现，流有着缓冲机制。**缓冲区**又称为**缓存**，它是内存空间的一部分。也就是说，在内存空间中预留了一定的存储空间，这些存储空间用来缓冲输入或输出的数据，这部分预留的空间就叫做缓冲区。缓冲区根据其对应的是输入设备还是输出设备，分为**==输入缓冲区==**和**==输出缓冲区==**。

输入或输出的内容会存在流对象对应的缓冲区，在特定情景下会从缓冲区释出。



- **为什么要引入缓冲区？**

  比如我们从磁盘里取信息，我们先把读出的数据放在缓冲区，计算机再直接从缓冲区中取数据，等缓冲区的数据取完后再去磁盘中读取，这样就可以减少磁盘的读写次数，再加上计算机对缓冲区的操作大大快于对磁盘的操作，故应用缓冲区可大大提高计算机的运行速度。

  又比如，我们使用打印机打印文档，由于打印机的打印速度相对较慢，我们先把文档输出到打印机相应的缓冲区，打印机再自行逐步打印，这时我们的 CPU 可以处理别的事情。因此缓冲区就是一块内存区，它用在输入输出设备和 CPU 之间，用来缓存数据。它使得低速的输入输出设备和高速的CPU 能够协调工作，避免低速的输入输出设备占用 CPU，解放出 CPU，使其能够高效率工作。



- **缓冲区要做哪些工作？**

  从上面的描述中，不难发现缓冲区向上连接了程序的输入输出请求，向下连接了真实的 I/O 操作。作为中间层，必然需要分别处理好与上下两层之间的接口，以及要处理好上下两层之间的协作。

  输入或输出的内容会存在流对象对应的缓冲区，在特定情景下会从缓冲区释出。



- ==**缓冲机制**==

  缓冲机制分为三种类型：全缓冲、行缓冲和不带缓冲

  **全缓冲**：在这种情况下，**当==填满缓冲区==后才进行实际 I/O 操作**。全缓冲的典型代表是==对磁盘文件的读写==。

  **行缓冲**：在这种情况下，**当在输入和输出中遇到==换行符==时，执行真正的 I/O 操作**。这时，我们输入的字符先存放在缓冲区，等按下回车键换行时才进行实际的 I/O 操作。==典型代表是`cin`==。

  **不带缓冲**：也就是不进行缓冲，有多少数据就刷新多少。==标准错误输出`cerr`==是典型代表，这使得出错信息可以直接尽快地显示出来。



`cout`既有**全缓冲**的机制，又有**行缓冲**的机制；

`cin`通常体现**行缓冲**机制；

`cerr`属于**不带缓冲**机制，通常用于处理错误信息。



### 标准输出流	`ostream`

ostream 类定义了全局输出流对象==cout==，即标准输出，在缓冲区刷新时将数据输出到终端。



**如下几种情况会导致输出缓冲区内容被刷新：**

1. <font color=red>**程序正常结束**</font>；

2. <font color=red>**缓冲区满**</font>；
3. 使用<font color=red>**操纵符**</font>显式地刷新输出缓冲区，如`endl`



来看一个简单的例子：在使用`cout`时，如果在输出流语句末尾使用了`endl`函数，会进行换行，并刷新缓冲区

``` c++
void test0(){
    for(int i = 0; i < 1025; ++i){
        cout << 'a'; 
    }
    sleep(2); // 先输出1024个a，等待两秒程序结束刷新缓冲区再输出一个a
}
```



如果在使用`cout`时，没有使用`endl`函数，键盘输入的内容会存在输出流对象的缓冲区中，当**缓冲区满**或**遇到换行符**时，将缓冲区刷新，内容传输到终端显示。可使用sleep函数查看缓冲的效果。

``` c++
#include <unistd.h>
void test0(){
    for(int i = 0; i < 1024; ++i){
        cout << 'a';
    }
    sleep(2);
    cout << 'b'; // 等待两秒，输出了1024个a（缓冲区满了，继续写入才刷新）
    sleep(2);
} // 函数结束，离开局部作用域，输出b
```

GCC中标准输出流的默认缓冲区大小就是**1024个字节**。



如果不用sleep函数，即使没有endl或换行符，所有内容依然是直接输出

——因为**程序执行完时也会刷新缓冲区**。



- **关于操作符**

`endl`: 用来完成**换行**，并刷新缓冲区

`ends`: 在输入后加上一个**空字符('\0')**，然后再**刷新缓冲区**

`flush`: 用来**直接刷新缓冲区**的	`cout.flush()`



查看ostream头文件中endl的实现（刷新缓冲区+换行）

![image-20231114141954274](./cppbase.assets/image-20231114141954274.png)



- **标准错误流**

ostream 类还定义了全局输出流对象==cerr==，标准错误流（不带缓冲）

试试看如下的代码运行会有什么效果

``` c++
#include <unistd.h>
void test1(){
	cerr << 1;
	cout << 3;
	sleep(2);                                                           
}
```



## 文件输入输出流（重点）	`ifstream`	`ofstream`	`fstream`

所谓“文件”，一般指存储在外部介质上数据的集合。一批数据是以文件的形式存放在外部介质上的。操作系统是以文件为单位对数据进行管理的。要向外部介质上存储数据也必须先建立一个文件（以文件名标识），才能向它输出数据。外存文件包括磁盘文件、光盘文件和U盘文件。目前使用最广泛的是磁盘文件。

文件流是以外存文件为输入输出对象的数据流。

**文件输入流**是从外存文件流向内存的数据，**文件输出流**是从内存流向外存文件的数据。每一个文件流都有一个内存缓冲区与之对应。文件流本身不是文件，而只是以文件为输入输出对象的流。若要对磁盘文件输入输出，就必须通过文件流来实现。



- **C++ 对文件进行操作的流类型有三个:**

  ==ifstream （文件输入流）==

  ==ofstream（文件输出流）==

  ==fstream  （文件输入输出流）==



他们的构造函数形式都很类似:

``` c++
ifstream();
explicit ifstream(const char* filename, openmode mode = ios_base::in);
explicit ifstream(const string & filename, openmode mode = ios_base::in);

ofstream();
explicit ofstream(const char* filename, openmode mode = ios_base::out);
explicit ofstream(const string & filename, openmode mode = ios_base::out);

fstream();
explicit fstream(const char* filename, openmode mode = ios_base::in|out);
explicit fstream(const string & filename, openmode mode = ios_base::in|out);
```



**补充**：==explicit关键字==的意义——禁止隐式转换

```
class Point{
public:
    //这个关键字加到隐式转换时需要调用的构造函数前
    //禁止隐式转换
    // explicit
    Point(int x = 0, int y = 0)
    : _ix(x)
    , _iy(y)
    { cout << "Point(int,int)" << endl; }

    void print() const{
        cout << "(" << _ix
            << "," << _iy
            << ")" << endl;
    }
    
private:
    int _ix;
    int _iy;
};

void test0(){
    Point pt(1,2);
    Point pt2(10);
    Point pt3;
    Point pt4 = Point(2,6);
    Point pt5 = Point();
    Point(); //利用无参构造创建一个临时Point对象
    
    //隐式转换
    // Point pt6 = Point(1);
    Point pt6 = 1;
    pt6.print();
    //这样写实际上是声明了一个返回类型Point的函数
    // Point pt3();
    // pt3.print(); // error
    // 正确的无参构造
    // Point pt3;
}

void test1(){
	
}
```



### 文件输入流	`ifstream`

#### 文件输入流对象的创建

首先我们要明确使用文件输入流的信息传输方向：==文件 -----> 文件输入流对象的缓冲区 -----> 程序中的数据结构==

根据上述的说明，我们可以将输入流对象的创建分为两类：

1. 可以使用**==无参构造==**创建ifstream对象，再==使用**open函数**将这个文件输入流对象与文件绑定==（若文件不存在，则文件输入流进入failbit状态）；

2. 也可以使用**==有参构造==**创建ifstream对象，在==创建时就将流对象与文件绑定==，后续操作这个流对象就可以对文件进行相应操作。

通过参考文档中对ifstream的构造函数的描述，文件输入流对象的有参构造需要输入文件名，可以指定打开模式（不指定则使用**in模式**，为读打开）

``` c++
#include <fstream>
void test0(){
    // 用无参的方式创建，再open
    ifstream ifs;
    ifs.open("test1.cc"); // 文件输入流对象是从文件中读取内容，需要绑定到一个<存在的>文件
    
    // 有参构造
    ifstream ifs2("test2.cc");
    
    string filename = "test3.cc";
    ifstream ifs3(filename);
}
```



```c++
void test0(){
    ifstream ifs;
    //文件输入流对象是从文件中读取内容
    //需要绑定到一个<存在的>文件
    /* ifs.open("try.txt"); */
    ifs.open("explicit.cc");

    if(!ifs.good()){
        cout << "ifstream open file fail!" << endl;
        return;
    }
    
    //默认以换行符、空格作为间隔符
    //一次读取一个字符串
    string word;
    //只要ifs是goodbot状态就会一直读取
    while(ifs >> word){
        cout << word << endl;
    }

    //规范操作，使用完之后关闭流
    ifs.close();
}
```



- **文件模式**

根据不同的情况，对文件的读写操作，可以采用不同的文件打开模式。文件模式在 GNU GCC7.4 源码实现中，是用一个叫做 openmode 的枚举类型定义的，它位于 ios_base 类中。文件模式一共有六种，它们分别是:

==`in`: 输入，文件将允许做**读**操作；如果文件不存在，打开失败==

==`out`: 输出，文件将允许做**写**操作；如果文件不存在，则直接创建一个==

==`app`: 追加，写入将始终发生在文件的末尾==

==`ate`: 末尾，写入最初在文件的末尾==

==`trunc`: 截断，如果打开的文件存在，其内容将被丢弃，其大小被截断为零==

==`binary`: 二进制，读取或写入文件的数据为二进制形式==

<img src="./cppbase.assets/image-20240302194238908.png" alt="image-20240302194238908" style="zoom:80%;" />



- <font color=red>**explicit关键字**</font>

explicit关键字的作用是**禁止隐式转换**，以Point类为例，如果这个类提供了只使用一个int型数据就可以完成初始化的构造函数，那么写出如下语句时，是可以完成隐式转换的（将一个int型数据转换成了Point对象）

如果不希望隐式转换通过，就在被调用的这个构造函数前加上explicit关键字，这样Point pt4 = 10这种语句就会报错。

``` c++
class Point{
public:
    Point(int ix = 0, int iy = 0)
    : _ix(ix)
    , _iy(iy)
    {
        cout << "Point(int,int)" << endl;
    }
private:
    int _ix;
    int _iy;
};

void test0(){
    Point pt4 = 10;//ok
    pt4.print();
}
```



#### 按行读取

- 方法一：==使用ifstream类中的**成员函数getline**==，这种方式是兼容C的写法


![image-20231114153418881](./cppbase.assets/image-20231114153418881.png)

![image-20231114165346910](./cppbase.assets/image-20231114165346910.png)

``` c++
ifstream ifs("test.cc");
//方法一，兼容C的写法，使用较少
char buff[100] = {0};
while(ifs.getline(buff,sizeof(buff))){
    cout << buff << endl;
    memset(buff,0,sizeof(buff));
}
```

准备好一片空间存放一行的内容，但是有一个弊端就是我们并不知道一行的内容会有多少个字符，如果超过了设置的字符长度将无法完成该行的读取，也将跳出循环。



- **方法二：**==使用&lt;string&gt;提供的**getline**方法==，工作中更常用

![image-20231114172656364](./cppbase.assets/image-20231114172656364.png)

<img src="./cppbase.assets/image-20231114172732337.png" alt="image-20231114172732337" style="zoom:80%;" />

<img src="./cppbase.assets/image-20231114172826427.png" alt="image-20231114172826427" style="zoom:80%;" />

三个参数：传入输入流对象、接收用的string、分隔符（默认换行符为分隔符）

``` c++
//更方便，使用更多
string line;
while(getline(ifs,line)){
    cout << line << endl;
}
```

将一行的内容交给一个string对象去存储，不用再关心字符数了。

```c++
void test0(){
    ifstream ifs;
    //文件输入流对象是从文件中读取内容
    //需要绑定到一个存在的文件
    /* ifs.open("try.txt"); */
    ifs.open("explicit.cc");

    if(!ifs.good()){
        cout << "ifstream open file fail!" << endl;
        return;
    }
 
    //方式一：兼容C的写法
    char buff[100] = {0};
    while(ifs.getline(buff,sizeof(buff))){
        cout << buff << endl;
        memset(buff,0,sizeof(buff));
    }

    //方式二：使用C++的string的getline函数
    string line;
    while(std::getline(ifs,line)){
        cout << line << endl;
    }

    //规范操作，使用完之后关闭流
    ifs.close();
}
```



#### 读取指定字节数的内容

==read函数== + ==seekg函数== + ==tellg函数==

通过文件输入流对象读取到的内容交给字符数组，同时需要传入要读取的字符数

<img src="./cppbase.assets/image-20231121220137273.png" alt="image-20231121220137273" style="zoom:80%;" />

<img src="./cppbase.assets/image-20231121220333589.png" alt="image-20231121220333589" style="zoom:80%;" />

要知道字符数就需要用上tellg函数了，可以这样理解，从文件中读取内容时存在一个**文件游标**，读取是从文件游标的位置开始读取的。==tellg就是用来获取游标位置==的，而==seekg则是用来设置游标位置==的。

<img src="./cppbase.assets/image-20231121220941483.png" alt="image-20231121220941483" style="zoom:80%;" />

调用seekg时有两种方式，一种是**绝对位置**`seekg(size_t pos)`（比如将游标设为流的开始位置，可以直接传参数0）；一种是**相对位置**`seekg(off,dir)`，off代表偏移量，dir代表锚点（游标偏移的基准点），传入偏移量和基准点——第一个参数：相对基准点需要向前偏移则传入负数，不偏移则传入0，需要向后偏移则传入正数；第二个参数格式为<span style=color:red;background:yellow>**std::ios::beg**</span>(以流的开始位置为例)

<img src="./cppbase.assets/image-20231121221123214.png" alt="image-20231121221123214" style="zoom: 80%;" />



<img src="./cppbase.assets/image-20240312202315654.png" alt="image-20240312202315654" style="zoom:50%;" />

例子：读取一个文件的全部内容

``` c++
void test0(){
    string filename = "test.cc";
    ifstream ifs(filename); 

    if(!ifs){
        cerr << "ifs open file fail!";
        return;
    }
    
    //读取一个文件的所有内容先要获取文件的大小
    //将游标放到了文件的最后
    fs.seekg(0,std::ios::end);
    long length = ifs.tellg() + 1;
    cout << length << endl;

    char * pdata = new char[length + 1]();
    //需要将游标再放置到文件开头
    ifs.seekg(0,std::ios::beg);
    ifs.read(pdata,length);

    //content包含了文件的所有内容，包括空格、换行
    string content(pdata);
    cout << "content:" << content << endl;
    /* cout << pdata << endl; */
    ifs.close();
}

```

还可以在创建输入流对象时指定ate模式，省去第一步将游标置流末尾处的操作。



### 文件输出流	`ofstream`

文件输出流的作用是**==将流对象保存的内容传输给文件==**

<img src="./cppbase.assets/image-20231121223550199.png" alt="image-20231121223550199" style="zoom:80%;" />

ofstream对象的创建与ifstream对象的创建类似

``` c++
#include <fstream>
void test0(){
    ofstream ofs;
    ofs.open("test1.cc");
    
    ofstream ofs2("test2.cc");
    
    string filename = "test3.cc";
    ofstream ofs3(filename);
}
```

推测一下，如果文件输出流对象绑定的文件不存在，可以吗？



- <font color=red>**通过输出流运算符写内容**</font>

ofstream对象绑定文件后，可以往该文件中写入内容

``` c++
string filename = "test3.cc";
ofstream ofs3(filename);

string line("hello,world!\n");
ofs << line; 

ofs.close();
```

内容传输的过程是string中的内容传给ofs对象，再传给这个对象绑定的文件。



但是我们会发现进行多次写入，并没有保留下多次的内容，因为这种创建方式会使**打开模式默认为==std::ios::out==**，每次都会**==清空==**文件的内容。

为了实现在文件流结尾追加写入内容的效果，可以在创建流对象时指定打开模式为<span style=color:red;background:yellow>**std::ios::app**</span>（追加模式）

``` c++
string filename = "test3.cc";
ofstream ofs3(filename，std::ios::app);
```



- **通过==write函数==写内容**

除了使用输出流运算符<< 将内容传输给文件输出流对象（传给ofstream对象就是将内容传到其绑定的文件中），还可以使用write函数进行传输

<img src="./cppbase.assets/image-20231121224635781.png" alt="image-20231121224635781" style="zoom:80%;" />

``` c++
char buff[100] = "hello,world!";
ofs.write(buff,strlen(buff));
```



- 动态查看指令

为了更方便地查看多次写入的效果（动态查看文件的内容）可以使用指令

```C++
tail 文件名 -F   //动态查看文件内容
ctrl + c        //退出查看
```

<img src="./cppbase.assets/image-20231121224849472.png" alt="image-20231121224849472" style="zoom:80%;" />



## 字符串输入输出流	`istringstream`	`ostringstream`	`stringstream`

字符串I/O是内存中的字符串对象与字符串输入输出流对象之间做内容传输的数据流，通常用来做格式转换。

C++ 对字符串进行操作的流类型有三个: 

`istringstream`	字符串输入流

`ostringstream`	字符串输出流

`stringstream`	字符串输入输出流

它们的构造函数形式都很类似:

``` c++
istringstream(): istringstream(ios_base::in) { }
explicit istringstream(openmode mode = ios_base::in);
explicit istringstream(const string& str, openmode mode = ios_base::in);

ostringstream(): ostringstream(ios_base::out) { }
explicit ostringstream(openmode mode = ios_base::out);
explicit ostringstream(const string& str, openmode mode = ios_base::out);

stringstream(): stringstream(in|out) { }
explicit stringstream(openmode mode = ios_base::in|ios_base::out);
explicit stringstream(const string& str, openmode mode = ios_base::in|ios_base::out);
```



### 字符串输入流	`istringsteam`

**==将字符串的内容传输给字符串输入流对象==，再通过这个对象进行字符串的处理（解析）**

创建字符串输入流对象时传入c++字符串，字符串的内容就被保存在了输出流对象的缓冲区中。之后可以通过输入流运算符将字符串内容输出给不同的变量，起到了字符串分隔的作用。

![image-20231123173647213](./cppbase.assets/image-20231123173647213.png)

——如下，将字符串s的内容传给了两个int型数据

``` c++
void test0(){
    string s("123 456");
    int num = 0;
    int num2 = 0;
    //将字符串内容传递给了字符串输入流对象  
    istringstream iss(s);
    iss >> num >> num2;
    cout << "num:" << num << endl;
    cout << "num2:" << num2 << endl;
}
```

因为输入流运算符会默认以**==空格符==**作为分隔符，字符串`123 456`中含有一个空格符，那么传输时会将空格前的`123`传给num，空格后的`456`传给num2，因为num和num2是int型数据，所以编译器会以int型数据来理解缓冲区释出的内容，将num和num2赋值为`123`和`456`



**字符串输入流通常用来处理字符串内容，比如==读取配置文件==**

```c++
// myserver.conf
ip 192.168.0.0
port 8888
dir ~HaiBao/53th/day06
    
// readConf.cc
void readConfig(const string & filename){
    ifstream ifs(filename);
    if(!ifs.good()){
        cout << "open file fail!" << endl;
        return;
    }
    
    string line;
    string key, value;
    while(getline(ifs,line)){
        istringstream iss(line);
        iss >> key >> value;
        cout << key << " -----> " << value << endl; 
    }
}

void test0(){
    readConfig("myserver.conf");
}
```



`istringstream` 是基于流缓冲区的，它不需要显式的关闭操作。这是因为 `istringstream` 对象在超出作用域时会自动析构，其析构函数会负责清理所有的资源。这是C++ RAII（Resource Acquisition Is Initialization）原则的一个应用，即资源在对象创建时获取，在对象销毁时释放。



### 字符串输出流	`ostringstream`

通常的用途就是将各种类型的数据转换成字符串类型

``` c++
void test0(){
    int num = 123, num2 = 456;
    ostringstream oss;
    // 把所有的内容都传给了字符串输出流对象
    oss << "num = " << num << " , num2 = " << num2 << endl;
    cout << oss.str() << endl;
}
```

将字符串、int型数据、字符串、int型数据统统传给了字符串输出流对象，存在其缓冲区中，利用它的str函数，全部转为string类型并完成拼接。



# 第四章 日志系统

日志系统在整个系统架构中的重要性可以称得上基础的基础，但是这一点，都容易被大多数人所忽视。因为日志在很多人看来只是printf，在系统运行期间，很难一步一步地调试，只能根据系统的运行轨迹来推断错误出现的位置，而日志往往也是最重要的参考资料。

日志系统主要解决的问题就是记录系统的运行轨迹，在这个基础上，进行跟踪分析错误，审计系统运行流程。一般在高可靠的系统中，是不允许系统运行终止的，所以也会产生海量的日志。

日志系统的内容可以分为两类：

1. **业务级别**的日志，主要供终端用户来分析他们业务过程；
2. **系统级别**的日志，供开发者维护系统的稳定。

由于日志系统的数据输出量比较大，所以不能不考虑对整个系统性能的影响。从另外一方面来看，海量的日志内容有时候并不件好事，因为，很容易覆盖真实问题的蛛丝马迹，也增加日志阅读者信息检索的困难。所以日志系统的设计需要挑选一个合适的工具，并进行合理的设计。

在github上有一个项目叫awesome-cpp，其中收录了与cpp有关的各种项目，在其中有一个logging分类，列举了各种常用的日志系统工具。

我们的课程中学习log4cpp，之后的项目阶段将会使用到。

[fffaraz/awesome-cpp: A curated list of awesome C++ (or C) frameworks, libraries, resources, and shiny things. Inspired by awesome-... stuff. (github.com)](https://github.com/fffaraz/awesome-cpp?tab=readme-ov-file#logging)
![](https://bray07.oss-cn-beijing.aliyuncs.com/image-20240224181545923.png)



##  日志系统的设计

日志系统的设计，一般而言要抓住最核心的一条，就是日志从产生到到达最终目的地期间的处理流程。一般而言，为了设计一个灵活可扩展，可配置的日志库，主要将日志库分为4个部分去设计，分别是：记录器、过滤器、格式化器、输出器四部分。

- **==记录器==（日志来源Category）**：负责产生日志记录的**原始信息**，比如（原始信息，日志优先级——门槛，超过多少优先级才记录日志，时间，记录的位置）等等信息。

- **==过滤器==（日志系统优先级Priority）**：负责按指定的过滤条件**过滤**掉我们不需要的日志。

- **==格式化器==（日志布局Layout）**：负责对原始日志信息按照我们想要的格式去**格式化**。

- **==输出器==（日志目的地Appender）**：负责将将要进行记录的日志（一般经过过滤器及格式化器的处理后）**记录到日志目的地**（例如：输出到文件中）。



下面以一条日志的生命周为例说明日志库是怎么工作的。

==**一条日志的生命周期：**==

1.  产生：`info(“log information.”);`
2.  经过记录器，记录器去获取日志发生的时间、位置、线程信息等等信息；
3.  经过过滤器，决定是否记录；
4.  经过格式化器处理成设定格式后传递给输出器。例如输出`“2018-3-22 10:00:00 [info] log information.”`这样格式的日志到文件中。日志的输出格式由格式化器实现，输出目的地则由输出器决定；
5.  这条日志信息生命结束。



##  log4cpp的安装

下载压缩包

下载地址：https://sourceforge.net/projects/log4cpp/files/

安装步骤

```shell
$ tar xzvf log4cpp-1.1.4rc3.tar.gz

$ cd log4cpp

$ ./configure  //进行自动化构建，自动生成makefile

$ make

$ sudo make install //安装  把头文件和库文件拷贝到系统路径下
    
//安装完后
//默认头文件路径：/usr/local/include/log4cpp
//默认lib库路径：/usr/local/lib
```

打开log4cpp官网：[Log for C++ Project (sourceforge.net)](https://log4cpp.sourceforge.net/)

拷贝simple example的内容，编译运行



编译指令：

```c++
g++ log4cppTest.cc -llog4cpp -lpthread
```



**可能报错：找不到动态库**

解决方法：

```
cd /etc
```

<img src="./cppbase.assets/image-20231124114253211.png" alt="image-20231124114253211" style="zoom:80%;" />

```
sudo vim ld.so.conf
```

将默认的lib库路径写入，再重新加载：

![image-20231124115107112](./cppbase.assets/image-20231124115107112.png)

```
sudo vim ld.so.con
```

`ld.so.cache`执行了`sudo ldconfig`之后，会更新该缓存文件，会将所有动态库信息写入到该文件当可执行程序需要加载相应动态库时，会从这里查找。

完成这些操作后，再使用上面的编译指令去编译示例代码。



##  log4cpp的核心组件

官网的simple example中包含了四个核心组件，这个代码需要完全理解其用法。

利用已学过的类与对象的知识对这段示例代码进行解读和推测。

Simple example：

```C++
// main.cpp

#include "log4cpp/Category.hh"
#include "log4cpp/Appender.hh"
#include "log4cpp/FileAppender.hh"
#include "log4cpp/OstreamAppender.hh"
#include "log4cpp/Layout.hh"
#include "log4cpp/BasicLayout.hh"
#include "log4cpp/Priority.hh"

int main(int argc, char** argv) {
    // 1
    // Appender代表输出器（目的地）
    // 基类指针指向派生类对象
	log4cpp::Appender *appender1 = new log4cpp::OstreamAppender("console", &std::cout);
    // 输出器绑定布局器（格式化器）
	appender1->setLayout(new log4cpp::BasicLayout());

	log4cpp::Appender *appender2 = new log4cpp::FileAppender("default", "program.log");
	appender2->setLayout(new log4cpp::BasicLayout());
	
    // 2
    // 日志记录器
    // 创建了一个root的Category对象
    // 日志是从Category出发
	log4cpp::Category& root = log4cpp::Category::getRoot();
    
    // 3
    // 设置root优先级
	root.setPriority(log4cpp::Priority::WARN);
    
    // 4
    // 设置root的目的地
	root.addAppender(appender1);

    // 创建了一个sub1的Category对象
    // 他是root的子对象
	log4cpp::Category& sub1 = log4cpp::Category::getInstance(std::string("sub1"));
    // 子对象又添加了目的地
	sub1.addAppender(appender2);

	// use of functions for logging messages
	root.error("root error");
	root.info("root info");
	sub1.error("sub1 error");
	sub1.warn("sub1 warn");

	// printf-style for logging variables
	root.warn("%d + %d == %s ?", 1, 1, "two");

	// use of streams for logging messages
	root << log4cpp::Priority::ERROR << "Streamed root error";
	root << log4cpp::Priority::INFO << "Streamed root info";
	sub1 << log4cpp::Priority::ERROR << "Streamed sub1 error";
	sub1 << log4cpp::Priority::WARN << "Streamed sub1 warn";

	// or this way:
	root.errorStream() << "Another streamed error";

	return 0;
}
```

Console output for that example

```c++
1352973121 ERROR  : root error
1352973121 ERROR sub1 : sub1 error
1352973121 WARN sub1 : sub1 warn
1352973121 WARN  : 1 + 1 == two ?
1352973121 ERROR  : Streamed root error
1352973121 ERROR sub1 : Streamed sub1 error
1352973121 WARN sub1 : Streamed sub1 warn
1352973121 ERROR  : Another streamed error
```



### 日志目的地（Appender）

通过log4cpp官网查看常用类的信息

https://log4cpp.sourceforge.net/

![image-20231124150134123](./cppbase.assets/image-20231124150134123.png)



我们关注这三个目的地类，点开后查看它们的构造函数（名字都是字符串）

**• ==OstreamAppender==		C++通用输出流(如 cout)**

OstreamAppender的构造函数传入两个参数：**目的地名**、**输出流指针**

```c++
OstreamAppender (const std::string &name, std::ostream *stream)
```

**• ==FileAppender==		写到本地文件中**

FileAppender的构造函数传入两个参数：**目的地名**、**保存日志的文件名**（后面两个参数使用默认值即可，分别表示以结尾附加的方式的保存日志，当前用户读写-其他用户只读）

```c++
FileAppender (const std::string &name, const std::string &fileName, bool append=true, mode_t mode=00644)
```

**• ==RollingFileAppender==		写到回卷文件中**

```c++
RollingFileAppender (const std::string &name, const std::string &fileName, 
                     size_t maxFileSize=10 *1024 *1024, unsigned int maxBackupIndex=1, 
                     bool append=true, mode_t mode=00644)
```

```
name：附加器的名称，用于在日志系统中标识这个RollingFileAppender。"rollerAppender"
fileName：日志文件的名称，包括路径。
maxFileSize：日志文件的最大大小，超过这个大小就会触发回卷。
maxBackupIndex：保留的回卷文件的最大数量。
append（可选）：一个布尔值，指示是否在现有文件的末尾追加内容，默认为true。
mode（可选）：文件的模式和权限，默认为00644，表示所有者具有读写权限，组和其他用户具有只读权限。
```

RollingFileAppender稍复杂一些，如果没有回卷文件，将所有的日志信息都保存在一个文件中，那么随着系统的运行，产生越来越多的日志，本地日志文件会越变越大，若不加限制，则会大量占用存储空间。所以通常的做法是使用回卷文件，比如只给日志文件1G的空间，对于这1G的空间可以再次进行划分，比如使用10个文件存储日志信息，每一个文件最多100M.

RollingFileAppender构造函数的参数如上，其中要注意的是回卷文件个数，如果这一位传入的参数是9，那么实际上会有10个文件保存日志。

**回卷的机制：**

首先生成一个wd.log文件，该文件存满后接着写入日志，那么wd.log文件改名为wd.log.1

然后再创建一个wd.log文件，将日志内容写入其中，wd.log文件存满后接着写入日志，wd.log.1文件改名为wd.log.2，wd.log改名为wd.log.1

再创建一个wd.log文件，将最新的日志内容写入。

以此类推，直到wd.log和wd.log.1、wd.log.2、... wd.log.9全都存满后再写入日志，wd.log.9（其中实际上保存着最早的日志内容）会被舍弃，编号在前的回卷文件一一进行改名，再创建新的wd.log文件保存最新的日志信息。

```c++
#include <iostream>
#include "log4cpp/Category.hh"
#include "log4cpp/OstreamAppender.hh"
#include "log4cpp/FileAppender.hh"
#include "log4cpp/RollingFileAppender.hh"
#include "log4cpp/PatternLayout.hh"
#include "log4cpp/Priority.hh"
using namespace std;
using namespace log4cpp;

//设计个性化的日志布局需要使用PatternLayout
void test0(){
    //1.设置日志布局
    PatternLayout * ptn1  = new PatternLayout();
    ptn1->setConversionPattern("%d %c [%p] %m%n");

    PatternLayout * ptn2  = new PatternLayout();
    ptn2->setConversionPattern("%d %c [%p] %m%n");

    PatternLayout * ptn3  = new PatternLayout();
    ptn3->setConversionPattern("%d %c [%p] %m%n");

    //2.创建输出器对象
    OstreamAppender * pos = new OstreamAppender("console",&cout);
    //输出器与布局绑定
    pos->setLayout(ptn1);

    FileAppender * filePos = new FileAppender("file","wd.log");
    filePos->setLayout(ptn2);

    RollingFileAppender * rfPos = new RollingFileAppender("rollingfile","rollingfile.log",5 * 1024,9);
    rfPos->setLayout(ptn3);

    //3.创建日志记录器
    //引用名salesDepart是在代码中使用的，表示Category对象
    //参数中salesDepart是获取日志来源时返回的记录器的名字
    //一般让两者相同，方便理解
    Category & salesDepart = Category::getInstance("salesDepart");

    //4.给Category设置优先级
    salesDepart.setPriority(Priority::ERROR);

    //5.给Category设置输出器
    salesDepart.addAppender(pos);
    salesDepart.addAppender(filePos);
    salesDepart.addAppender(rfPos);

    //6.记录日志

    int count = 100;
    while(count-- > 0){
        salesDepart.emerg("this is an emerge msg");
        salesDepart.fatal("this is a fatal msg");
        salesDepart.alert("this is an alert msg");
        salesDepart.crit("this is a crit msg");
        salesDepart.error("this is an error msg");
        salesDepart.warn("this is a warn msg");
        salesDepart.notice("this is a notice msg");
        salesDepart.info("this is a info msg");
    }

    //7.日志系统退出时，回收资源
    Category::shutdown();
}

int main(void){
    test0();
    return 0;
}
```



### 日志布局（Layout）

示例代码中使用的是==BasicLayout==，也就是默认的日志布局，这样一条日志最开始的信息就是日志产生时距离1970.1.1的秒数，不方便观察。

实际使用时可以用<span style=color:red;background:yellow>**PatrrenLayout**</span>对象来定制化格式，类似于printf的格式化输出

![image-20231124163239081](./cppbase.assets/image-20231124163239081.png)



使用new语句创建日志布局对象，通过指针调用**==setConversionPattern==**来设置日志布局

![image-20231124164249912](./cppbase.assets/image-20231124164249912.png)

``` c++
PatternLayout * ptn1 = new PatternLayout();
ptn1->setConversionPattern("%d %c [%p] %m%n"); 
```

setConversionPattern函数接收一个string作为参数，格式化字符的意义如下：

**%d %c [%p] %m%n**

**时间 模块名 优先级 消息本身 换行符**



<span style=color:red;background:yellow>**注意（极易出错）：**</span>

**当日志系统有多个日志目的地时，==每一个目的地Appender都需要设置一个布局Layout==（一对一关系）**



### 日志记录器（Category）

创建Category对象时：

1）用`getRoot()`先创建**root模块**对象，对root模块对象设置**优先级**和**目的地**；

2）再用`getInstance()`创建**叶模块**对象，叶模块对象会**继承**root模块对象的优先级和目的地，可以再去修改优先级、目的地



**补充：**如果没有创建根对象，直接使用getInstance创建叶对象，会先隐式地创建一个Root对象。



**子Category可以继承父Category的信息：==优先级==、==目的地==**

<img src="./cppbase.assets/image-20231124171810154.png" alt="image-20231124171810154" style="zoom:80%;" />



官网示例代码中Category对象的创建：先创建根对象，再创建叶对象

``` c++
log4cpp::Category& root = log4cpp::Category::getRoot();
root.setPriority(log4cpp::Priority::WARN);
root.addAppender(appender1);

log4cpp::Category& sub1 = log4cpp::Category::getInstance(std::string("sub1")); //传入的字符串sub1就会是日志中记录下的日志来源
sub1.addAppender(appender2);

// 如何创建sub1的子对象sub11
// using namespace log4cpp; ...
// Category & sub11 = sub1.getInstance(std::string("sub11"));
```



也可以一行语句创建叶对象

``` c++
log4cpp::Category& sub1 = log4cpp::Category::getRoot().getInstance("salesDepart"); // 记录的日志来源会是salesDepart
sub1.setPriority(log4cpp::Priority::WARN);
sub1.addAppender(appender1);
```



这里需要注意的是，例子中sub1本质上是绑定Category对象的引用，在代码中利用sub1去进行设置优先级、添加目的地、记录日志等操作；

`getInstance()`的参数`"salesDepart"`表示的是日志信息中记录的Category名称，也就是日志来源 —— 对应了布局中的`%c`

所以一般在使用时这两者的名称取同一个名称，统一起来，能够更清楚地知道该条日志是来源于`"salesDepart"`这个模块



### 日志优先级（Priority）

对于 log4cpp 而言，有两个优先级需要注意，一个是**日志记录器**的优先级，另一个就是**某一条日志**的优先级。Category对象就是日志记录器，在使用时须设置好其优先级；某一行日志的优先级，就是Category对象在调用某一个日志记录函数时指定的级别，如 `logger.debug("this is a debug message")` ，这一条日志的优先级就是DEBUG级别的。简言之：

**日志系统有一个优先级A，日志信息有一个优先级B**

**只有B高于或等于A的时候，这条日志才会被输出（或保存），当B低于A的时候，这条日志会被过滤；**

```C++
class LOG4CPP_EXPORT Priority {
public:
	typedef enum {
			EMERG = 0,
			FATAL = 0,
			ALERT = 100,
			CRIT = 200,
			ERROR = 300,
			WARN = 400,
			NOTICE = 500,
			INFO = 600,
			DEBUG = 700,
			NOTSET = 800
	} PriorityLevel;
	// ......
}; // 数值越小，优先级越高；数值越大，优先级越低。
```



##  定制日志系统

模仿示例代码的形式去设计定制化的日志系统

![image-20231124173810374](./cppbase.assets/image-20231124173810374.png)

![image-20231124173816155](./cppbase.assets/image-20231124173816155.png)

在设计日志系统时多次使用了new语句，这些核心组件的构造函数具体细节我们也并不清楚，但可以知道的是这个过程必然会申请资源，所以规范的写法在日志系统退出时要调用**shutdown**回收资源。

```c++
#include <iostream>
#include "log4cpp/Category.hh"
#include "log4cpp/OstreamAppender.hh"
#include "log4cpp/FileAppender.hh"
#include "log4cpp/PatternLayout.hh"
#include "log4cpp/Priority.hh"
using namespace std;
using namespace log4cpp;

//设计个性化的日志布局需要使用PatternLayout
void test0(){
    //1.设置日志布局
    PatternLayout * ptn1  = new PatternLayout();
    ptn1->setConversionPattern("%d %c [%p] %m%n");

    PatternLayout * ptn2  = new PatternLayout();
    ptn2->setConversionPattern("%d %c [%p] %m%n");

    //2.创建输出器对象
    OstreamAppender * pos = new OstreamAppender("console",&cout);
    //输出器与布局绑定
    pos->setLayout(ptn1);

    FileAppender * filePos = new FileAppender("file","wd.log");
    filePos->setLayout(ptn2);

    //3.创建日志记录器
    //引用名salesDepart是在代码中使用的，表示Category对象
    //参数中salesDepart是获取日志来源时返回的记录器的名字
    //一般让两者相同，方便理解
    Category & salesDepart = Category::getInstance("salesDepart");

    //4.给Category设置优先级
    salesDepart.setPriority(Priority::ERROR);

    //5.给Category设置输出器
    salesDepart.addAppender(pos);
    salesDepart.addAppender(filePos);

    //6.记录日志
    salesDepart.emerg("this is an emerge msg");
    salesDepart.fatal("this is a fatal msg");
    salesDepart.alert("this is an alert msg");
    salesDepart.crit("this is a crit msg");
    salesDepart.error("this is an error msg");
    salesDepart.warn("this is a warn msg");
    salesDepart.notice("this is a notice msg");
    salesDepart.info("this is a info msg");

    //7.日志系统退出时，回收资源
    Category::shutdown();
}

int main(void){
    test0();
    return 0;
}
```



## log4cpp的单例实现

留下一个比较有挑战性的作业：

用所学过的类和对象的知识，封装log4cpp，让其使用起来更方便，要求：可以像printf一样，同时输出的日志信息中最好能有文件的名字，函数的名字及其所在的行号(这个在C/C++里面有对应的宏，可以查一下)

代码模板：

Mylogger.hpp

```c++
#ifndef __Mylogger_HPP__
#define __Mylogger_HPP__

#include "log4cpp/Category.hh"
#include <string>
#include <iostream>
using std::string;

#define addPrefix(msg) string("[").append(__FILE__) \
                                 .append(":").append(__func__)\
                                 .append(":").append(std::to_string(__LINE__))\
                                 .append("]").append(msg).c_str()

#define LogWarn(msg) Mylogger::getInstance()->warn(addPrefix(msg))
#define LogError(msg) Mylogger::getInstance()->error(addPrefix(msg))

class Mylogger
{
public:
	void warn(const char *msg);
	void error(const char *msg);
	void debug(const char *msg);
	void info(const char *msg);
	
    static Mylogger * getInstance();
    static void destroy();

private:
	Mylogger();
	~Mylogger();
    
private:
    log4cpp::Category & _mycat;     // 只需要加上日志记录器
    static Mylogger * _pInstance;   // 单例模式使用的静态指针，保存创建的单例对象
};
#endif
```

Mylogger.cc

```c++
#include "Mylogger.hpp"
#include <iostream>
#include "log4cpp/OstreamAppender.hh"
#include "log4cpp/FileAppender.hh"
#include "log4cpp/Priority.hh"
#include "log4cpp/PatternLayout.hh"
using std::cout;
using std::endl;
using namespace log4cpp;

Mylogger * Mylogger::_pInstance = nullptr;

Mylogger::Mylogger()
// : _mycat(Category::getRoot().getInstance("mycat"))
: _mycat(Category::getInstance("mycat"))
{
    // 1.日志布局器（与输出器必须一对一，所以要创建两个）
    auto ptn1 = new PatternLayout();
    ptn1->setConversionPattern("%d %c [%p] %m%n");
    auto ptn2 = new PatternLayout();
    ptn2->setConversionPattern("%d %c [%p] %m%n");

    // 2.创建日志输出器对象，与布局器进行绑定
    auto pos = new OstreamAppender("console",&cout);
    pos->setLayout(ptn1);
    auto pfile = new FileAppender("fileApp","wd.log");
    pfile->setLayout(ptn2);

    // 3.日志优先级
    _mycat.setPriority(Priority::DEBUG);

    // 4.给category设置输出器
    _mycat.addAppender(pos);
    _mycat.addAppender(pfile);

    cout << "Mylogger()" << endl;
}

Mylogger::~Mylogger(){
    Category::shutdown();
    cout << "~Mylogger()" << endl;
}

Mylogger* Mylogger::getInstance(){
    if(_pInstance == nullptr){
        _pInstance = new Mylogger();
    }
    return _pInstance;
}

void Mylogger::destroy(){
    if(_pInstance){
        delete _pInstance;
        _pInstance = nullptr;
    }
}

void Mylogger::warn(const char * msg)
{
    _mycat.warn(msg);
}
void Mylogger::error(const char * msg)
{
    _mycat.error(msg);
}
void Mylogger::debug(const char * msg)
{
    _mycat.debug(msg);
}
void Mylogger::info(const char * msg)
{
    _mycat.info(msg);
}
```

MyloggerTest.cc

```c++
#include "Mylogger.hpp"
#include <string>
#include <iostream>
using std::cout;
using std::endl;
using std::string;

void test0(){
    LogError("The log is error message");
    LogWarn("The log is warn message");
    Mylogger::getInstance()->debug(addPrefix("The log is debug message"));
    Mylogger::getInstance()->info(addPrefix("The log is info message"));
    Mylogger::destroy();
}

void test1(){
    cout << addPrefix("hello") << endl;
}

int main(void){
    test0();
    return 0;
}
```



## log4cpp配置文件的读取

配置文件：

```
log4cpp.rootCategory=DEBUG, rootAppender
log4cpp.category.sub1=DEBUG, A1, A2
log4cpp.category.sub1.sub2=DEBUG, A3

log4cpp.appender.rootAppender=ConsoleAppender
log4cpp.appender.rootAppender.layout=PatternLayout
log4cpp.appender.rootAppender.layout.ConversionPattern=%d [%p] %m%n 

log4cpp.appender.A1=FileAppender
log4cpp.appender.A1.fileName=A1.log
log4cpp.appender.A1.layout=BasicLayout

log4cpp.appender.A2=FileAppender
log4cpp.appender.A2.threshold=WARN
log4cpp.appender.A2.fileName=A2.log
log4cpp.appender.A2.layout=PatternLayout
log4cpp.appender.A2.layout.ConversionPattern=%d [%p] %m%n 

log4cpp.appender.A3=RollingFileAppender
log4cpp.appender.A3.fileName=A3.log
log4cpp.appender.A3.maxFileSize=200
log4cpp.appender.A3.maxBackupIndex=1
log4cpp.appender.A3.layout=PatternLayout
log4cpp.appender.A3.layout.ConversionPattern=%d [%p] %m%n 
```



读取：

```c++
#include <log4cpp/Category.hh>
#include <log4cpp/PropertyConfigurator.hh>

int main(int argc, char* argv[])
{
	std::string initFileName = "log4cpp.properties";
	log4cpp::PropertyConfigurator::configure(initFileName);

	log4cpp::Category& root = log4cpp::Category::getRoot();

	log4cpp::Category& sub1 = 
		log4cpp::Category::getInstance(std::string("sub1"));

	log4cpp::Category& sub2 = 
		log4cpp::Category::getInstance(std::string("sub1.sub2"));

	root.warn("Storm is coming");

	sub1.debug("Received storm warning");
	sub1.info("Closing all hatches");

	sub2.debug("Hiding solar panels");
	sub2.error("Solar panels are blocked");
	sub2.debug("Applying protective shield");
	sub2.warn("Unfolding protective shield");
	sub2.info("Solar panels are shielded");

	sub1.info("All hatches closed");

	root.info("Ready for storm.");

	log4cpp::Category::shutdown();

	return 0;
}
```



# 第五章 运算符重载

## 友元

- 什么叫友元？

一般来说，类的私有成员只能在类的内部访问，类之外是不能访问它们的。但如果将其他类/函数设置为类的友元，那么友元类/函数就可以**在前一个类的==类定义之外访问其私有成员==**了。<span style=color:red;background:yellow>**用friend关键字声明友元**</span>。

将类比作一个家庭，类的private成员相当于家庭的秘密，一般的外人当然不允许探听这些秘密的，只有friend才有资格探听这些秘密。

友元的三种形式：**普通函数、成员函数、友元类**。



### 友元之==普通函数==形式

示例：程序中有Point类，需要求取两个点的距离。按照设想，我们定义一个普通函数distance，接收两个Point对象作为参数，通过公式计算这两个点之间的距离。但Point的_ix和 _iy是私有成员，在类外不能通过对象访问，那么可以**将distance函数声明为Point类的友元函数**，之后就可以在distance函数中访问Point的私有成员了。

``` c++
class Point{
public:
    Point(int x, int y)
    : _ix(x)
    , _iy(y)
    {}

    friend float distance(const Point & lhs, const Point & rhs);
    
private:
    int _ix;
    int _iy;
};

float distance(const Point & lhs, const Point & rhs){
    return sqrt((lhs._ix - rhs._ix)*(lhs._ix - rhs._ix) +
                (lhs._iy - rhs._iy)*(lhs._iy - rhs._iy));
}
```



### 友元之==成员函数==形式

假设类A有一个成员函数，该成员函数想去访问另一个类B类中的私有成员变量。这时候则可以在第二个类B中，**声明第一个类A的那个成员函数为类B的友元函数**，这样第一个类A的某个成员函数就可以访问第二个类B的私有成员变量了。

我们试验一下，以另一种方式实现上面的需求，如果distance函数不再是一个普通函数，而是Line类的一个成员函数，也就是说需要**在一个类（Line）的成员函数中==访问另一个类（Point）的私有成员==**，那么又该如何实现呢？

- 如果将Point类定义在Line类之前，Line类的成员函数要访问Point类的私有成员，需要在Point类中将Line的这个成员函数设为友元函数——此时编译器并不认识Line类；

- 如果将Line类定义在Point类之前，那么distance函数需要接受两个const Point &作为参数——此时编译器不认识Point类；



解决方法：

——在Line前面做一个Point类的前向声明；

——但如果将distance的函数体写在Line类中，编译器虽然知道了有一个Point类，但并不知道Point类具体有什么成员，所以此时在函数体中访问_ix、 _iy都会报错，编译器并不认识它们；

思考一下，有什么办法可以解决这个问题呢？

``` c++
//前向声明
class Point;

class Line{
public:
	float distance(const Point & lhs, const Point & rhs){
        return sqrt((lhs._ix - rhs._ix)*(lhs._ix - rhs._ix) +
                (lhs._iy - rhs._iy)*(lhs._iy - rhs._iy)); // =====> error
    }
};

class Point{
public:
    Point(int x, int y)
    : _ix(x)
    , _iy(y)
    {}

    friend float Line::distance(const Point & lhs, const Point & rhs);
private:
    int _ix;
    int _iy;
};

```



```c++
#include <math.h>
#include <iostream>
using std::cout;
using std::endl;

class Point;//前向声明

class Line{
public:
    //友元的成员函数形式
    float distance(const Point & lhs, const Point & rhs);
};

class Point{
public:
    Point(int x,int y)
        : _ix(x)
          , _iy(y)
    {}

    friend float Line::distance(const Point & lhs, const Point & rhs);
    
private:
    int _ix;
    int _iy;
};

float Line::distance(const Point & lhs, const Point & rhs){
    return sqrt(pow(lhs._ix - rhs._ix,2) + pow(lhs._iy - rhs._iy,2));
}

void test0(){
    Point pt(0,0);
    Point pt2(3,4);
    Line line;
    cout << line.distance(pt,pt2) << endl;
}

int main(void){
    test0();
    return 0;
}
```



**补充：**

前向声明的用处：进行了前向声明的类，可以以引用或指针的形式作为函数的参数，只要不涉及到对该类对象具体成员的访问，编译器可以通过。（让编译器认识这个类，但是注意如果只前向声明，这个类的具体实现没有的话，无法使用这个类的对象，无法创建）

**注意：**==友元的声明==要注意和==函数的形式==完全相同（参数列表，返回值）



### ==友元类==

如上的例子，假设类 Line 中不止有一个 distance 成员函数，还有其他成员函数，它们都需要访问 Point 的私有成员，如果还像上面的方式一个一个设置友元，就比较繁琐了，可以直接将 Line 类设置为 Point 的友元类，在工作中这也是更常见的方法。

``` c++
//前向声明
class Point;

class Line{
public:
    // friend class Point; // error 识别不到Point的私有成员
	float distance(const Point & lhs, const Point & rhs){
        return sqrt((lhs._ix - rhs._ix)*(lhs._ix - rhs._ix) +
                (lhs._iy - rhs._iy)*(lhs._iy - rhs._iy)); // error
    }
};

class Point{
public:
    friend class Line; // 将 Line 类设置为 Point 的友元类
    
    Point(int x, int y)
    : _ix(x)
    , _iy(y)
    {}

    friend float Line::distance(const Point & lhs, const Point & rhs);
private:
    int _ix;
    int _iy;
};

```

在 Point 类中声明 Line 类是本类的友元类，那么Line类中的所有成员函数中都可以访问Point类的私有成员。一次声明，全部解决。

```c++
#include <math.h>
#include <iostream>
using std::cout;
using std::endl;

class Point{
public:
    Point(int x,int y)
        : _ix(x)
          , _iy(y)
    {}
    
    void print() const{
        cout << "(" << _ix
            << "," << _iy
            << ")" << endl;
    }

    //声明了Line类是Point类的友元类
    friend class Line;

private:
    int _ix;
    int _iy;
};

class Line{
public:
    float distance(const Point & lhs, const Point & rhs);
    void setX(Point & rhs,int x){
        rhs._ix = x;
    }
};

float Line::distance(const Point & lhs, const Point & rhs){
    return sqrt(pow(lhs._ix - rhs._ix,2) + 
                pow(lhs._iy - rhs._iy,2));
}

void test0(){
    Point pt(0,0);
    Point pt2(3,4);
    Line line;
    cout << line.distance(pt,pt2) << endl;

    line.setX(pt,1);
    pt.print();
}

int main(void){
    test0();
    return 0;
}
```

不可否认，友元将类的私有成员暴露出来，在一定程度上破坏了信息隐藏机制，似乎是种“副作用很大的药”，但俗话说“良药苦口”。好工具总是要付出点代价的，拿把锋利的刀砍瓜切菜，总是要注意不要割到手指的。

友元的存在，使得类的接口扩展更为灵活，使用友元进行运算符重载从概念上也更容易理解一些，而且， C++ 规则已经极力地将友元的使用限制在了一定范围内。



### 友元的特点

1. **友元不受类中访问权限的限制**——可访问私有成员
2. **友元破坏了类的封装性**
3. **不能滥用友元 ，友元的使用受到限制**
4. **友元是单向的**——A类是B类的友元类，则A类成员函数中可以访问B类私有成员；但并不代表B类是A类的友元类，如果A类中没有声明B类为友元类，此时B类的成员函数中并不能访问A类私有成员
5. **友元不具备传递性**——A是B的友元类，B是C的友元类，无法推断出A是C的友元类
6. **友元不能被继承**——因为友元破坏了类的封装性，为了降低影响，设计层面上友元不能被继承



## 运算符重载

### 运算符重载的介绍

C++ 预定义中的运算符的操作对象只局限于基本的内置数据类型，但是对于自定义的类型是没有办法操作的。当然我们可以定义一些函数来实现这些操作，但考虑到用运算符表达含义的方式很简洁易懂，当定义了自定义类型时，也希望这些运算符能被自定义类类型使用，以此提高开发效率，增加代码的可复用性。为了实现这个需求，C++提供了运算符重载。其指导思想是：<span style=color:red;background:yellow>**希望自定义类类型在操作时与内置类型保持一致**</span>。



能够重载的运算符有42个

| +    | -    | *    | /      | %     | ^        |
| ---- | ---- | ---- | ------ | ----- | -------- |
| &    | \|   | ~    | !      | =     | <        |
| >    | +=   | -=   | *=     | /=    | %=       |
| ^=   | &=   | \|=  | >>     | <<    | >>=      |
| <<=  | ==   | !=   | >=     | <=    | &&       |
| \|\| | ++   | --   | ->*    | ->    | ,        |
| []   | ()   | new  | delete | new[] | delete[] |

==不能重载的运算符包括==

```
.	成员访问运算符
.*	成员指针访问运算符
?:	三目运算符
::	作用域限定符
sizeof 长度运算符
```

记法：**带点的运算符**不能重载，加上**sizeof**



### 运算符重载的规则与形式（重点）

- **运算符重载有以下规则**

  1. 运算符重载时 ，<font color=red>**其操作数类型必须要是==自定义类==类型或==枚举==类型**</font> ——**不能是内置类型**

  2. 其优先级和结合性还是固定不变的       a == b + c

  3. <font color=red>**操作符的操作数个数是保持不变的**</font>

  4. <font color=red>**运算符重载时，不能设置默认参数**</font>     ——如果设置了默认值，其实也就是改变了操作数的个数

  5. 逻辑与 && 逻辑或 || 就不再具备短路求值特性 ，进入函数体之前必须完成所有函数参数的计算, 不推荐重载

  6. 不能臆造一个并不存在的运算符         @ $ 、



- **运算符重载的形式**：运算符重载的形式有三种

  1. <span style=color:red;background:yellow>**采用<u>友元函数</u>的重载形式**</span>

  2. 采用**<u>普通函数</u>**的重载形式

  3. <span style=color:red;background:yellow>**采用<u>成员函数</u>的重载形式**</span>



以加法运算符为例，认识这三种形式。



### +运算符重载

需求：实现一个复数类，复数分为实部和虚部，重载+运算符，使其能够处理两个复数之间的加法运算（实部加实部，虚部加虚部）

#### 友元函数实现

我们可以定义一个普通函数，接收两个复数类对象，在这个函数中定义计算逻辑。因为要在类外访问Complex的私有成员，故可以将这个普通函数设为Complex的友元函数

``` c++
class Complex{
    //...
    friend Complex add(const Complex & lhs, const Complex & rhs);
private:
    int _real;
    int _image;
};

Complex add(const Complex & lhs, const Complex & rhs){
    return Complex(lhs._real + rhs._real,
                   lhs._image + rhs._image);
}

void test0(){
    Complex cx(1,2);
    Complex cx2(3,4);
    Complex cx3 = add(cx,cx2); //这样就可以计算两个Complex对象的加法了
}
```



还想要更直观、更简洁一些，那么可以定义一个相应的**运算符重载函数（operator+）**，就可以直接使用+完成这两个对象的加法运算了

``` c++
class Complex{
    //...
    friend Complex operator+(const Complex & lhs, const Complex & rhs);
private:
    int _real;
    int _image;
};

Complex operator+(const Complex & lhs, const Complex & rhs){
    return Complex(lhs._real + rhs._real,
                   lhs._image + rhs._image);
}

void test0(){
    Complex c1(1,2);
    Complex c2(3,4);
    Complex c3 = c1 + c2;
    Complex c4 = operator+(c1,c2); // 本质
    c3.print();
    c4.print();
}
```



**运算符重载的本质是定义一个运算符重载函数，步骤如下**

1. 先确定这个函数的**返回值是什么类型**（加法运算返回值应该是一个临时的Complex对象，所以此处返回类型为Complex）
2. 再写上**函数名**（operator + 运算符，此处就是**operator+**）
3. 再补充**参数列表**（考虑这个运算符有几个操作数，此处加法运算应该有两个操作数，分别是两个Complex对象，因为加法操作不改变操作数的值，可以用const引用作为形参）
4. 最后完成函数体的内容（此处直接调用Complex构造函数创建一个新的对象作为返回值）。



——在定义的operator+函数中需要访问Complex类的私有成员，要进行友元声明

<span style=color:red;background:yellow>**像加号这一类<u>不会修改操作数的值</u>的运算符，倾向于采用友元函数的方式重载。**</span>



#### 普通函数实现（不推荐）

在一个普通函数中想要访问一个类的私有成员，也可以给这个类添加一些**==公有的get系列函数==**，因为这些成员函数是可以访问私有成员的，而在类外可以通过对象直接调用这些成员函数，也就能获取到私有成员了。

实际工作中不推荐使用，因为这样做**几乎完全失去了对私有成员的保护**。

``` c++
class Complex {
public:
	//...
	int getReal() const { return _real; }
	int getImage() const { return _image; }
private:
    int _real;
    int _image;
};

Complex operator+(const Complex & lhs, const Complex & rhs)
{
	return Complex(lhs.getReal() + rhs.getReal(),
				   lhs.getImage() + rhs.getImage());
}

void test0()
{
    Complex c1(1,2);
    Complex c2(3,4);
    Complex c3 = c1 + c2;
    Complex c4 = operator+(c1,c2); // 本质
    c3.print();
    c4.print();
}
```



#### 成员函数实现

还可以将运算符重载函数定义为Complex类的成员函数

``` c++
class Complex{
public:
public:
    Complex(int real,int image)
    : _real(real)
    , _image(image)
    {}

    void print() const{
        cout << _real << "+" << _image << "i" << endl;
    }

    Complex operator+(const Complex & rhs){
        return Complex(_real + rhs._real, _image + rhs._image);
    }

private:
    int _real;
    int _image;
};
```



**这种写法要注意的是，加法运算符的==左操作数实际上就是this指针所指向的对象，在参数列表中只需要写上右操作数==**

```C++
Complex cp1(1,2);
Complex cp2(3,4);
Complex cp = cp1 + cp2; // 本质是Complex cp = cp1.operator+(cp2)  
```



——思考，如果我们写出了这样的代码，是否可以通过呢？可以，但是要避免

``` c++
class Complex{
public:
	//...
	Complex operator+(const Complex & rhs)
	{
		return Complex(_real - rhs._real, _image - rhs._image);
	}
};
```

明明是加操作符，但函数内却进行的是减法运算，这是合乎语法规则的，不过却有悖于人们的直觉思维，会引起不必要的混乱。

因此，除非有特别的理由，**尽量使重载的运算符与其内置的、广为接受的语义保持一致**。



### +=运算符重载

如果要让Complex对象能够使用+=运算符进行计算，需要对+=运算符进行重载。

==像+=这一类**会修改操作数的值**的运算符，倾向于采用**成员函数**的方式重载。==

同样按照上述步骤来定义运算符重载函数，请尝试实现：

``` c++
Complex & operator+=(const Complex & rhs){
    cout << "operator+=" << endl;
    _real += rhs._real;
    _image += rhs._image;
    return *this;
}
```



### 重载形式的选择（重要）

* **==不会==修改操作数的值**的运算符，倾向于采用**==*友元函数*==**的方式重载
* **==会==修改操作数的值**的运算符，倾向于采用**==成员函数==**的方式重载
* **赋值=、下标[ ]、调用()、成员访问->、成员指针访问->* 运算符**必须是**成员函数**形式重载
* 与给定类型密切相关的运算符，如**递增**、**递减**和**解引用**运算符，通常应该是**成员函数**形式重载
* 具有**对称性**的运算符可能转换任意一端的运算对象，例如**相等性**、**位运算符**等，通常应该是***友元形式***重载



### ++运算符重载

自增运算符有前置++和后置++两种形式，依然按照内置类型先分析计算逻辑，再类比这个计算逻辑去定义运算符重载函数

int a = 5; 

 a++的操作是使a的值增为6，但是<u>这个表达式的返回值却是一个临时变量（a的值改变前的副本，即5）</u>

++a则是使a的值增加到6，<u>直接返回变量a本身</u>

类比Complex，写出++运算符重载函数。按照我们目前的认知，前置++和后置++都应该选择成员函数的形式进行重载。

但是前置形式和后置形式都是只有一个操作数（本对象），参数完全相同的情况下，只有返回类型不同不能构成重载。前置形式和后置形式的区分只能通过设计层面人为地加上区分。

``` c++
// 前置++的形式
// 返回类型是引用
Complex & operator++(){
    cout << "Complex & operator++()" << endl;
    ++_real;
    ++_image;
    return *this;
}

// 后置++的形式
// 参数列表中要多加一个int 与前置形式进行区分
// 返回类型是对象，不是引用
Complex operator++(int){
    cout << "Complex operator++(int)" << endl;
    Complex tmp(*this);
    ++_real;
    ++_image;
    return tmp;
}
```



### [ ]运算符重载

需求：定义一个CharArray类，模拟char数组，需要通过下标访问运算符能够对对应下标位置字符进行访问。

- 分析[ ]运算符重载函数的返回类型，因为通过下标取出字符后可能进行写操作，需要改变CharArray对象的内容，所以应该用char引用；

- [ ]运算符的操作数有两个，一个是CharArray对象，一个是下标数据，ch[0]的本质是ch.operator[] (0)；

函数体实现需要考虑下标访问越界情况，若未越界则返回对应下标位置的字符，若越界返回终止符。

``` c++
class CharArray{
public:
    CharArray(const char * pstr)
    : _capacity(strlen(pstr) + 1)
    , _data(new char[_capacity]())
    {
        strcpy(_data,pstr);
    }

    ~CharArray(){
        if(_data){
            delete [] _data;
            _data = nullptr;
        }
    }

    char & operator[](size_t idx){
        if(idx < _capacity - 1){
            return _data[idx];
        }else{
            cout << "out of range" << endl;
            static char nullchar = '\0';
            return nullchar;
        }
    }

    void print() const{
        cout << _data << endl;
    }
private:
    size_t _capacity;
    char * _data;
};
```

**思考**：如果要**==禁止CharArray对象通过下标访问修改字符数组中的元素==**，应该怎么办？

```c++
#include <string.h>
#include <iostream>
using std::cout;
using std::endl;

//需求：自定义一个类模拟char数组
class CharArray{
public:
    CharArray(const char * pstr)
    : _capacity(strlen(pstr) + 1)
    , _data(new char[_capacity]())
    {
        strcpy(_data,pstr);
    }

    ~CharArray(){
        if(_data){
            delete [] _data;
            _data = nullptr;
        }
    }

    void print() const{
        cout << _data << endl;
    }

    // 第一个const的效果，函数的返回值是一个const引用，调用函数得到结果<不允许进行写操作>
    // 第二个const的效果，在函数中<不能修改数据成员>，const对象只能调用const成员函数
    const char & operator[](size_t idx) const{
        if(idx < _capacity - 1){
            // _capacity = 100; // error
            // _data = new char[100](); // error
            // _data[idx] = 'Y';  // ok 可以修改 data是一个指针常量，指向不能修改，指向的内容可以修改
            return _data[idx];
        }else{
            cout << "out of range" << endl;
            static char nullchar = '\0';
            return nullchar;
        }
    }

private:
    size_t _capacity;
    char * _data;
};

void test0(){
    char arr[6] = "hello";
    cout << arr[0] << endl;
    arr[0] = 'H';
    cout << arr << endl;

    CharArray ca("hello");

    ca[0]; // ok
    ca.operator[](0); // 本质
    
    cout << ca[0] << endl; // 读操作
    // ca[0] = 'H'; // error 第一个const的效果 写操作
    ca.print();

    const CharArray ca2("world");
    cout << ca2[0] << endl;
    cout << ca2.operator[](0) << endl;
    // ca2[0] = 'W'; // error 第二个const的效果
    ca2.print();
}

int main(void){
    test0();
    return 0;
}
```



### 输入输出流运算符重载（重要）

#### 输出流运算符 <<

在之前的例子中，我们如果想打印一个对象时，常用的方法是通过定义一个 print 成员函数来完成，但使用起来不太方便。我们希望打印一个对象，与打印一个整型数据在形式上没有差别(如下例子)，那就必须要重载 << 运算符。

需求：

对于Complex对象，希望像内置类型数据一样，使用输出流运算符可以对其进行输出

分析：

- 输出流运算符有两个操作数，左操作数是输出流对象，右操作数是Complex对象。如果将输出流运算符函数写成Complex的成员函数，会带来一个问题，成员函数的第一个参数必然是this指针，也就是说Complex对象必须要作为左操作数。这种方式完成重载函数后，只能cx << cout这样来使用，与内置类型的使用方法不同，所以<span style=color:red;background:yellow>**输出流运算符的重载采用友元形式。**</span>

- cout << cx这个语句的返回值是cout对象，因为cout是全局对象，不允许复制，所以返回类型为ostream &；

- 参数列表中第一个是左操作数（cout对象），写出类型并给出形参名；第二个是右操作数（Complex对象），因为不会在输出流函数中修改它的值，采用const引用；

- 将Complex的信息通过连续输出语句全部输出给os，最终返回os（注意，使用cout输出流时通常会带上endl，那么在函数定义中就不加endl，以免多余换行）



``` c++
class Point {
public:
	// ...
	friend ostream & operator<<(ostream & os, const Point & rhs);

private:
	int _x;
	int _y;
};

ostream & operator<<(ostream & os, const Point & rhs)
{
	os << "(" << rhs._x << "," << rhs._y << ")";
	return os;
}

void test0(){
    Point pt(1,2);
    cout << pt << endl; // 本质形式： operator<<(cout,pt) << endl;
}
```



#### 输入流运算符 >>

需求：对于Complex对象，希望像内置类型数据一样，使用输入流运算符可以对其进行输入

实现过程与输出流类似

```` C++
class Point {
public:
	// ...
	friend istream & operator>>(istream & is, Point & rhs);
private:
	int _x;
	int _y;
};

istream & operator>>(istream & is, Point & rhs)
{
	is >> rhs._x;
	is >> rhs._y;
	return is;
}
````



——如果不想分开输出实部和虚部，也可以直接连续输入，空格符、换行符都能作为分隔符

``` c++
istream & operator>>(istream & is, Point & rhs)
{
	is >> rhs._x >> rhs._y;
	return is;
}
```



但是还有个问题需要考虑，使用输入流时需要判断是否是合法输入

——可以封装一个函数判断接收到的是合法的int数据，在>>运算符重载函数中调用，请结合前面输入流的知识试着实现

```c++
#include <iostream>
#include <limits>
using std::cout;
using std::cin;
using std::endl;
using std::ostream;
using std::istream;

// 第二个参数需要是引用形式，要确保写入的内容传给数据成员
void readInputInt(istream & is, int & number){
    cout << "please input a int number" << endl;
    // 实际的输入操作在这里
    while(is >> number, !is.eof()){
        if(is.bad()){
            cout << "istream has broken" << endl; 
            return;
        }else if(is.fail()){
            is.clear(); // 恢复流的状态
            is.ignore(std::numeric_limits<std::streamsize>::max(),'\n');
            cout << "please input a int number" << endl;
        }else{
            break;
        }
    }
}

class Complex
{
public:
    Complex(int real,int image)
        : _real(real)
          , _image(image)
    {}

    void print() const{
        cout << _real << "+"
            << _image << "i"
            << endl;
    }

    // 1.如果将<<运算符重载函数采用成员函数形式实现
    // 那么第一个参数只能是Complex对象，与内置类型的使用方式不符
    ostream & operator<<(ostream & os){
        os << _real << "+" << _image << "i" << endl;
        return os;
    }

    // 2.友元形式
    friend ostream & operator<<(ostream & os, const Complex & rhs);
    friend istream & operator>>(istream & is, Complex & rhs);

private:
    int _real;
    int _image;
};

// // 2.友元函数
ostream & operator<<(ostream & os, const Complex & rhs){
    os << rhs._real << "+" << rhs._image << "i";
    return os;
}

istream & operator>>(istream & is, Complex & rhs){
    // v1.0
    // cout << "please input a real:" << endl;
    // is >> rhs._real; // 缺少检错功能，输入不符合的数据，流崩了，程序没崩
    // cout << "please input an image:" << endl;
    // is >> rhs._image;

    // v2.0
    cout << "please input a real:" << endl;
    readInputInt(is,rhs._real);
    cout << "please input an image:" << endl;
    readInputInt(is,rhs._image);

    return is;
}

void test0(){
    Complex c1(1,2);

    // 1.采用成员函数形式
    // c1 << cout;
    // c1.operator<<(cout);
    // cout << c1; // error 编译错误
    
    // int a = 1, b = 2;
    // // 返回值就是cout
    // cout << a << b << endl;
    // // 返回值就是cin
    // cin >> a >> b;
    // cout << a << b << endl;

    // 2.友元形式
    cout << c1 << endl;
    operator<<(cout,c1) << endl; // 本质
    cin >> c1;
    cout << c1 << endl;
}

int main(void){
    test0();
    return 0;
}
```



### 成员访问运算符

成员访问运算符包括**箭头访问运算符（->）**和**解引用运算符（*）** ，它们是指针操作最常用的两个运算符。

我们先来看箭头运算符 -> ，**箭头运算符（->）==只能以<u>成员函数</u>的形式重载==**，其返回值**必须是一个==指针==或者==重载了箭头运算符的对象==**。来看下例子：

#### 两层结构下的使用

例子：建立一个双层的结构，MiddleLayer含有一个Data*型的数据成员

```c++
class Data{
public:
    Data()
    { cout << "Data()" << endl; }

    Data(int x)
    :_data(x)
    { cout << "Data()" << endl; }

    int getData() const{
        return _data;
    }

    ~Data(){
        cout << "~Data()" << endl;
    }

private:
    int _data = 10;
};

class MiddleLayer{
public:
    MiddleLayer(Data * p)
    :_pdata(p)   
    { cout << "MiddleLayer(Data*)" << endl; }

private:
    Data * _pdata;
};
```

Data*原生指针的用法如下，需要关注**堆空间资源的回收**

``` c++
Data * p = new Data();
p->getData();
//或(*p).getData();
delete p;
p = nullptr;
```

![image-20240314215645207](./cppbase.assets/image-20240314215645207.png)



**需求**：希望实现一个这样的效果，创建MiddleLayer对象ml，<u>让ml对象可以使用箭头运算符去调用Data类的成员函数`getData()`</u>

``` c++
/*需求*/
MiddleLayer ml(new Data);
cout << ml->getData() << endl;
```

箭头运算符无法应对MiddleLayer对象，那么可以定义**箭头运算符重载函数**。

1. 首先不用考虑重载形式，箭头运算符必须以**成员函数形式**重载

2. 然后考虑返回类型，返回值需要使用箭头运算符调用`getData()`函数，而原生的用法只有`Data*` 才能这么用，所以返回值应该是一个`Data*` ，此时应该直接返回`_pdata`

3. 同时考虑到一个问题：MiddleLayer的数据成员是一个`Data*`，创建MiddleLayer对象时初始化这个指针，让其指向了堆上的Data对象，那么还应该补充析构函数使MiddleLayer对象销毁时能够回收这片堆上的资源

``` c++
Data* operator->(){
    return _pdata;
}
```



**思考**：解引用运算符应该如何重载能够实现同样的效果呢？直接使用MiddleLayer对象模仿Data*指针去访问getData函数

```c++
class MiddleLayer{
public:
    MiddleLayer(Data * p)
    : _pdata(p)   
    { cout << "MiddleLayer(Data*)" << endl; }

    Data * operator->(){
        return _pdata;
    }

    Data & operator*(){
        return *_pdata;
    }

    ~MiddleLayer(){
        if(_pdata){
            delete _pdata;
            _pdata = nullptr;
        }
        cout << "~MiddleLayer()" << endl;
    }

private:
    Data * _pdata;
};

void test0(){
    // Data * p1 = new Data();
    // p1->getData();
    // (*p1).getData();
    // delete p1;
    // p1 = nullptr;
    
    MiddleLayer ml(new Data()); // ml是栈对象
    
    cout << ml->getData() << endl;
    cout << (ml.operator->())->getData() << endl; // 本质

    cout << (*ml).getData() << endl;
    cout << (ml.operator*()).getData() << endl; // 本质
}
```

当我们完成了以上的需求后，还有一件“神奇”的事情，使用的语句中有new没有delete，但是检查发现并没有内存泄漏

**原因：ml本身是一个局部对象，因为重载了箭头运算符和解引用运算符，所以看起来像个指针，也可以像指针一样进行使用，但是==这个对象在栈帧结束时会自动销毁==，自动调用析构函数回收了它的数据成员所申请的堆空间**

**实际上，这就是智能指针的雏形：其思想就是==通过对象的生命周期来管理资源==。**



#### 三层结构下的使用（难点）

- **拓展思考：那么如果结构再加一层，引入一个ThirdLayer类**

```c++
class ThirdLayer
{
public:
    ThirdLayer(MiddleLayer * pml)
    : _ml(pml)
    { cout << "ThirdLayer(MiddleLayer*)" << endl; }

    ~ThirdLayer(){
        if(_ml){
            delete _ml;
            _ml = nullptr;
        }
        cout << "~ThirdLayer()" << endl;
    }

private:
    MiddleLayer * _ml;
};
```



希望实现如下使用方式，思考一下应该如何对ThirdLayer进行对应的运算符重载

``` c++
ThirdLayer tl(new MiddleLayer(new Data));
cout << tl->getData() << endl;
cout << (*(*tl)).getData() << endl;
```



- 拓展思考：如果解引用的使用也希望和箭头运算符一样，一步到位

``` c++
ThirdLayer tl(new MiddleLayer(new Data));
cout << (*tl).getData() << endl;
```



代码：

```c++
#include <iostream>
using std::cout;
using std::endl;

class Data{
public:
    Data()
    { cout << "Data()" << endl; }

    int getData() const{
        return _data;
    }

    ~Data(){
        cout << "~Data()" << endl;
    }
private:
    int _data = 10;
};

class MiddleLayer{
public:
    MiddleLayer(Data * p)
    : _pdata(p)   
    { cout << "MiddleLayer(Data*)" << endl; }

    Data * operator->(){
        cout << "Data * operator->()" << endl;
        return _pdata;
    }

    Data & operator*(){
        cout << "Data & operator*()" << endl;
        return *_pdata;
    }

    ~MiddleLayer(){
        if(_pdata){
            delete _pdata;
            _pdata = nullptr;
        }
        cout << "~MiddleLayer()" << endl;
    }
    
    // friend class ThirdLayer;

private:
    Data * _pdata;
};

class ThirdLayer
{
public:
    ThirdLayer(MiddleLayer * pml)
    : _ml(pml)
    { cout << "ThirdLayer(MiddleLayer*)" << endl; }

    ~ThirdLayer(){
        if(_ml){
            delete _ml;
            _ml = nullptr;
        }
        cout << "~ThirdLayer()" << endl;
    }

    /* 箭头访问运算符-> */
    // 1.友元形式 但这还需要添加友元类，这并不是我们想要的
    // Data * operator->(){
    //     return (*_ml)._pdata;
    // }

    // 2.
    MiddleLayer & operator->(){
        cout << "MiddleLayer & operator->()" << endl;
        return *_ml;
    }

    /* 解引用运算符* */
    // 两步解引用方案1.友元形式
    // Data * operator*(){
    //     cout << "Data * operator*()" << endl;
    //     return (*_ml)._pdata;
    // }

    // 两步解引用方案2.
    // MiddleLayer & operator*(){
    //     cout << "MiddleLayer & operator*()" << endl;
    //     return *_ml;
    // }

    // 一步解引用的方案
    Data & operator*(){
        // return *((*_ml)._pdata);
        return *(*_ml);
    }

private:
    MiddleLayer * _ml;
};

void test0(){
    // Data * p1 = new Data();
    // p1->getData();
    // (*p1).getData();
    // delete p1;
    // p1 = nullptr;
    
    MiddleLayer ml(new Data()); // 栈对象
    
    cout << ml->getData() << endl;
    cout << (ml.operator->())->getData() << endl; // 本质

    cout << (*ml).getData() << endl;
    cout << (ml.operator*()).getData() << endl; // 本质
}

void test1(){
    ThirdLayer tl(new MiddleLayer(new Data()));

    /* 箭头访问运算符-> */
    cout << endl;
    cout << tl->getData() << endl;
    //第一次调用ThirdLayer的箭头运算符重载函数
    //返回的是一个MiddleLayer对象
    ( tl.operator->() )->getData();
    //因为之前已经在MiddleLayer中重载了箭头运算符
    //所以MiddleLayer对象可以调用本类的->运算符重载函数
    //返回的是一个Data*，就可以直接使用箭头运算符了
    ( (tl.operator->()).operator->() )->getData();

    /*  解引用运算符*
        两步解引用的方案：访问getData
        方案一：内层的*tl返回一个Data*
        方案二：内存够的*tl返回一个MiddleLayer对象
    */
    // (MiddleLayer已经进行过重载)
    // cout << endl;
    // cout << (*(*tl)).getData() << endl;
    // cout << endl;

    // 一步解引用的方案：*tl的返回值必须是<Data对象>才可以
    cout << (*tl).getData() << endl;
}

int main(void){
    test1();
    return 0;
}
```



#### 内存分析

三层的结构比较复杂，我们可以通过内存图的方式进行分析。

ThirdLayer对象的创建

``` c++
ThirdLayer tl(new MiddleLayer(new Data));
```

实际上的内存结构如图

![image-20231128114046770](./cppbase.assets/image-20231128114046770.png)

**创建和销毁的过程：**

创建tl对象时，调用ThirdLayer的构造函数，调用的过程中调用MiddleLayer的构造函数，在这个过程调用Data的构造。Data构造完才能完成MiddleLayer的指针数据成员初始化，MiddleLayer创建完毕，才能完成ThirdLayer的指针数据成员初始化。

tl销毁时，马上调用ThirdLayer的析构，执行delete _ml时，第一步调用MiddleLayer的析构，在这个过程中，会delete _pdata，会调用Data的析构函数。



##  可调用实体

讲到调用这个词，我们首先能够想到**普通函数**和**函数指针**，在学习了类与对象的基础知识后，还增加了**成员函数**，那么它们都被称为<font color=red>**可调用实体**</font>。事实上，根据其他的一些不同的场景需求，C++还提供了一些可调用实体，它们都是通过运算符重载来实现的。

**==可调用实体==**：**普通函数**、**==函数指针==**、**成员函数**、**==成员函数指针==**、**==函数对象==**



普通函数执行时，有一个特点就是**无记忆性**。一个普通函数执行完毕，它所在的函数栈空间就会被销毁，所以普通函数执行时的状态信息，是无法保存下来的，这就让它无法应用在那些需要对每次的执行状态信息进行维护的场景。大家知道，我们学习了类与对象以后，有了对象的存在，对象执行某些操作之后，**==只要对象没有销毁，其状态可以保留==**。

### 函数对象	`operator()`

想让对象像一个函数一样被调用

``` c++
class FunctionObject{
public:
    //第一个括号表示运算符（函数调用运算符）
    //第二个括号表示参数列表（无参形式）
    int operator()(){
        cout << "int operator()()" << endl;
        ++_cnt;
        return 1;
    }

    //可以定义多种函数调用运算符重载函数
    void operator()(int x){
        cout << "void operator(int)" << endl;
        ++_cnt;
        cout << x << endl;
    }

    int _cnt = 0;
};

void test0(){
    FunctionObject fo; // 无参构造
    // FunctionObject fo2(); // 声明了一个返回值为FunctionObject对象的函数，函数名是fo2
    // FunctionObject * p = new FunctionObject(); // 无参构造创建堆上对象
    fo(); // 让对象像一个函数一样被调用
    fo.operator()();
}
```

上面的代码看起来很奇怪，如果我们从运算符的视角出发，就是函数调用运算符`()`要处理FunctionObject对象，只需要实现一个==**函数调用运算符重载函数`operator()`**==即可。

函数调用运算符必须以成员函数的形式进行重载

``` c++
class FunctionObject{
    void operator()(){
        cout << "void operator()()" << endl;
    }
};

void test0(){
    FunctionObject fo;
    fo(); // ok
}
```

在定义 "()" 运算符的语句中，第一对小括号总是空的，因为它代表着我们定义的运算符名称，第二对小括号就是函数参数列表了，它与普通函数的参数列表完全相同。对于其他能够重载的运算符而言，操作数个数都是固定的，**但函数调用运算符不同，它的参数是根据需要来确定的， 并不固定**。

<span style=color:red;background:yellow>**重载了函数调用运算符的类的对象称为函数对象**</span>，由于参数列表可以随意扩展 ，所以可以有很多重载形式（对应了普通函数的多种重载形式）

``` c++
class FunctionObject{
public:
    // 第一个括号表示运算符（函数调用运算符）
    // 第二个括号表示参数列表（无参形式）
    void operator()(){
        cout << "FunctionObject operator()()" << endl;
        ++ _count;
    }

    int operator()(int x, int y){
        cout <<"operator()(int,int)" << endl;
        ++ _count;
        return x + y;
    }
    
    int _count = 0; // 携带状态
};

void test0(){
    FunctionObject fo;
  
    cout << fo() << endl;
    cout << fo.operator()() << endl; // 本质

    cout << fo(5,6) << endl;
    cout << fo.operator()(5,6) << endl; // 本质

    cout << "fo._count:" << fo._count << endl; // 记录这个函数对象被调用的次数
}
```

函数对象相比普通函数的优点：**==可以携带状态==**（函数对象可以封装自己的数据成员、成员函数，具有更好的面向对象的特性）

如上，可以记录函数对象被调用的次数，而普通函数只能通过全局变量做到（全局变量不够安全）



### 函数指针	`typedef void(*Function)(int);`

既然对象可以像一个函数一样去调用，那函数可不可以像一个对象一样去组织？

如果可以，那函数类型由什么决定呢，也就是说，如果把函数看作对象，如何从这些“对象”抽象出类来？       

在C的阶段就学习过函数指针，定义函数指针时要明确使用这个指针指向一个什么类型的函数（返回类型、参数类型都要确定）

``` c++
void print(int x){
    cout << "print:" << x << endl;
}

void display(int x){
    cout << "display:" << x << endl;
}

int main(void){
    // 省略形式
    void (*p)(int) = print; // 只能指向返回值为void，参数为int的函数
    p(4);
    p = display;
    p(9);
    
    // 完整形式
    void (*p2)(int) = &print;
    (*p2)(4);
    p2 = &display;
    (*p2)(9);
}
```

定义函数指针p后，可以指向print函数，也可以再指向display函数，并通过函数指针调用函数（两种方式——完整/省略）；

```c++
void print(int x){
    cout << "print:" << x << endl;
}

void display(int x){
    cout << "display:" << x << endl;
}

void show(){
    cout << "show:" << endl;
}

int down(int x){
    cout << "down:" << endl;
    return x;
}

void test0(){
    // 定义函数指针时就已经确定了
    // 这个函数指针只能指向<特定类型>的函数
    // <返回类型><参数类型>都确定了
    // 省略形式
    void (*p)(int) = print;
    p(4);

    p = display;
    p(5);

    // 完整形式
    void (*p2)(int) = &print;
    (*p2)(6);

    // p = show; // error，参数不一致
    // p = down; // error，返回值不同
}
```

——那么其实可以抽象出一个函数指针类，这个类的对象就是这个特定类型的函数指针

p和p2可以抽象出一个函数指针类型**==void(*)(int)==**  —— **逻辑类型，不能在代码中直接以这种形式写出**。

以前我们使用 typedef 可以定义类型别名，这段程序中函数指针p、p2的类型是void (*) (int)，但是C++中是没有这个类的（我们可以这样理解，但是代码不能这么写）

可以使用typedef定义这样的一个新类型

可以理解为是给 `void(*)(int)` 取类型别名为 Function

``` c++
typedef void(*Function)(int);
```

Function 类的对象可以这样使用，这个类的对象都是特定类型的函数指针，只能指向一种函数（这种函数的类型在定义函数指针类时就决定了）

```c++
// 将此类的<函数指针>的类型定名为Func
typedef void (*Func)(int);
void test1(){
    Func p = print;
    p(6);
    p(8);
    p = display;
    p(7);
}

输出：
print:6
print:8
display:7
```



### 成员函数指针

函数指针的用法熟悉后，顺势思考一个问题：成员函数能否也使用这种形式？如果可以，应该怎样定义一个成员函数指针

比如有这样一个类FFF，包含两个成员函数

```` c++
class FFF
{
public:
    void print(int x){
        cout << "FFF::print:" << x << endl;
    }

    void display(int x){
        cout << "FFF::display:" << x << endl;
    }

    void test()
    {}
};
````

定义一个函数指针要明确指针指向的函数的**返回类型**、**参数类型**，那么<span style=color:red;background:yellow>**定义一个成员函数指针还需要确定的是<u>这个成员函数是哪个类的成员函数（类的作用域）</u>**</span>

与普通函数指针不一样的是，<font color=red>**成员函数指针的定义和使用都需要使用<u>完整写法</u>**</font>，不能使用省略写法，定义时要完整写出指针声明，使用时要**完整写出解引用**（解出成员函数后接受参数进行调用）。另外，成员函数需要通过对象来调用，<font color=red>**成员函数指针也需要通过<u>对象</u>来调用**</font>。

```` c++
void test2(){
    // FFF::print这个写法显示出print是FFF类的成员函数
    // 成员函数指针的定义和使用<不能用省略>的写法
    // 定义成员函数指针的时候也需要<加上类作用域>
    void (FFF::*p)(int) = &FFF::print; // 必须完整写法
    FFF fff;

    // 先获取成员函数，再进行调用
    // .*是成员指针访问运算符，这里的指针指的是p
    (fff.*p)(6);

    p = &FFF::display;
    (fff.*p)(7);

    void (FFF::*p2)(int) = &FFF::print;
}
````

类比，也可以使用typedef来定义这种成员函数指针类,使用这个成员函数指针类的对象来调用FFF类的成员函数print

这里有一个要求，成员函数指针指向的成员函数需要是FFF类的**公有函数**

``` c++
// 将此类的成员函数指针的类型定名为MemberFunc
typedef void (FFF::*MemberFunction)(int);

void test3(){
    // 创建成员函数指针的时候就已经确定了
    // <返回类型>、<参数>、是哪个<类>的成员函数
    MemberFunc p = &FFF::print;

    FFF fff;
    // 成员指针访问运算符的<第一种形式>
    // 指针指的是p，这是一个成员函数指针
    (fff.*p)(10); // FFF::print:10

    p = &FFF::display;
    // p = &DDD::print; // error 不能指向其他类

    FFF * pff = new FFF();
    // 成员指针访问运算符的<第二种形式>
    (pff->*p)(11); // FFF::display:11

    pff = nullptr;
    (pff->*p)(12); // FFF::display:12
}
```

此时就出现了一个新的运算符 **".*"** —— <span style=color:red;background:yellow>**成员指针访问运算符的第一种形式。**</span>



FFF类对象还可以是一个堆上的对象

``` c++
FFF * fp = new FFF();
(fp->*mf)(65); // 通过指针调用成员函数指针
```

又引出了新的运算符 **"->*"** —— <span style=color:red;background:yellow>**成员指针访问运算符的第二种形式。**</span>



```
Data * p1 = new Data();
p1->getData();
(*p1).getData();
delete p1;
p1 = nullptr;
```



**成员函数指针的意义：**

1. **回调函数**：<font color=red>**将成员函数指针作为参数传递给其他函数**</font>，使其他函数能够在特定条件下调用该成员函数；
2. **事件处理**：将成员函数指针存储事件处理程序中，以便在特定事件发生时调用相应的成员函数；
3. **多态性**：通过将成员函数指针存储在基类指针中，可以实现多态性，在运行时能够去调用相应的成员函数。



### 空指针的使用

接着上面的例子，我们来看一段比较奇怪的代码

``` c++
fp = nullptr;
(fp->*mf)(34);
```

发现竟然是可以通过的并输出了正常的结果。难道空指针去调用成员函数指针没有问题吗？

事实上，**==空指针==去调用==成员函数==也好、==成员函数指针==也好，只要==*不涉及*到访问该类<u>数据成员</u>==，都是可以的。**

``` c++
class Bar{
public:
    void test0(){ cout << "Bar::test0()" << endl; }
    void test1(int x){ cout << "Bar::test1(): " << x << endl; }
    void test2(){ cout << "Bar::test2(): " << _data << endl; }

    int _data = 10;
};

void test0(){
    Bar * fp = nullptr;
    fp->test0();
    fp->test1(3);
    fp->test2(); // error: Segmentation fault 空指针不能访问数据成员
    // Bar::test0; // error test0不是一个静态的成员函数
    &Bar::test0; // 获取test0的地址（程序代码区），并不是调用，并没有Bar对象
}
```

结合内存图来分析

![image-20231128173635460](./cppbase.assets/image-20231128173635460.png)

空指针没有指向有效的对象。对于不涉及数据成员的成员函数，不需要实际的对象上下文，因此就算是空指针也可以调用成功。**对于涉及数据成员的成员函数，空指针无法提供有效的对象上下文，因此导致错误。**



**总结：**

<span style=color:red;background:yellow>**C++中<u>普通函数、函数指针、成员函数、成员函数指针、函数对象</u>，可以将它们概括为可调用实体。**</span>



## 类型转换函数

以前我们认识了普通变量的类型转换，比如说 int 型转换为 long 型， double 型转换为 int 型，接下来我们要讨论下类对象与其他类型的转换。转换的方向有:

1. 由其他类型向自定义类型转换

2. 由自定义类型向其他类型转换



- **由其他类型向自定义类型转换**

由其他类型向定义类型转换是由构造函数来实现的，只有当类中定义了合适的构造函数时，转换才能通过。这种转换，一般称为**==隐式转换==**。

之前我们见识了隐式转换，当时的例子中能够进行隐式转换的前提是Point类中有相应的构造函数，编译器会看用一个int型数据能否创建出一个Point对象，如果可以，就创建出一个临时对象，并将它的值复制给pt 

``` c++
Point pt = 1;
//等价于Point pt = Point(1);
```

这种隐式转换是比较奇怪的，一般情况下，不希望这种转换成立，所以可以在相应的构造函数之前加上explicit关键字，禁止这种隐式转换。



而有些隐式转换使用起来很自然，比如：

``` c++
string s1 = "hello,world";
```

这行语句其实也是隐式转换，利用C风格字符串构造一个临时的string对象，再调用string的拷贝构造函数创建s1



- **由自定义类型向其他类型转换——类型转换函数**

类型转换函数的形式是固定的：**==operator 目标类型(){        }==**

它有着如下的特征：

1. **必须是成员函数**

2. **没有返回类型**

3. **没有参数**

4. **在函数执行体中必须要返回目标类型的变量**



**(1)自定义类型向内置类型转换**

在Point类中定义这样的类型转换函数

``` c++
class Point{
public:
   //...
    operator int(){
        cout << "operator int()" << endl;
        return _ix + _iy;
    }
	//...
};
```

使用时就可以写出这样的语句（与隐式转换的方向相反）

``` c++
Point pt(1,2);
int a = 10;
//将Point类型对象转换成int型数据
a = pt;
cout << a << endl;
```



**（2）自定义类型向自定义类型转换**

自定义类型可以向内置类型转换，还可以向自定义类型转换，但要<span style=color:red;background:yellow>**注意将类型转换函数设为谁的成员函数**</span>



``` c++
Point pt(1,2);
Complex cx(3,4);
cx = pt;
cx.print();
```

如上，想要让Point对象转换成Complex对象，并对cx赋值，应该在Point类中添加目标类型的类型转换函数

```` c++
class Point
{
    //...
    operator Complex(){
        cout << "operator Complex()" << endl;
        return Complex(_ix,_iy);
    }
};
````



```c++
class Point;

class Complex
{
public:
    Complex(int real = 1,int image = 2)
        : _real(real)
          , _image(image)
    {}

    Complex(const Point & pt);

    void print() const{
        cout << _real << "+"
            << _image << "i"
            << endl;
    }
private:
    int _real;
    int _image;
};

class Point{
public:
    explicit
        Point(int x = 0, int y = 0)
        : _ix(x)
          , _iy(y)
    { cout << "Point(int,int)" << endl; }

    void print() const{
        cout << "(" << _ix
            << "," << _iy
            << ")" << endl;
    }

    operator int(){
        cout << "operator int()" << endl;
        return _ix + _iy;
    }

    operator Complex(){
        cout << "operator Complex()" << endl;
        return Complex(_ix,_iy);
    }

    friend class Complex;

private:
    int _ix;
    int _iy;
};

Complex::Complex(const Point & rhs)
: _real(rhs._ix)
, _image(rhs._iy)
{}

void test0(){
    // 隐式转换，从内置类型转换成自定义类型
    // Point pt = 1; // 加上explicit以避免

    Point pt2(1,2);
    // 利用类型转换函数，将自定义类型转换成了内置类型
    int a = pt2;
    int b = pt2.operator int(); // 本质
    cout << a << endl;
    cout << b << endl;
}
```

思考，可否用隐式转换的思路（即调用特定形式的构造函数），实现这种转换？

```
void test1(){
    Point pt(1,2);
    Complex cx(3,4);

    // 隐式转换，cx = Complex(8);
    cx = 8;
    cx.print();

    // 隐式转换，cx = Complex(pt);
    cx = pt;
    cx.print();
}
```



## 附录：C++运算符优先级排序与结合性

<img src="./cppbase.assets/image-20231128114539734.png" alt="image-20231128114539734" style="zoom:80%;" />

<img src="./cppbase.assets/image-20231128114546612.png" alt="image-20231128114546612" style="zoom: 80%;" />



## 嵌套类

首先介绍两个概念：

- 类作用域（Class Scope）

类作用域是指==在**类定义内部**的范围==。在这个作用域内定义的成员（包括变量、函数、类型别名等）可以被该类的所有成员函数访问。类作用域开始于类定义的左花括号，结束于类定义的右花括号。在类作用域内，成员可以相互访问，无论它们在类定义中的声明顺序如何。



- 类名作用域（Class Name Scope）

类名作用域指的是==**可以通过类名访问**的作用域==。这主要用于访问类的**静态成员**、**嵌套类型**。类名必须用于访问静态成员或嵌套类型，除非在类的成员函数内部，因为它们不依赖于类的任何特定对象。以静态成员为例：

``` c++
class MyClass
{
public:
    void func(){
        _a = 100; // 类的成员函数内访问_a
    }
    static int _a;
};
int MyClass::_a = 0;//类名作用域

void test0(){
    MyClass::_a = 200; // 类外部访问_a
}
```



在函数和其他类定义的外部定义的类称为**全局类**，绝大多数的 C++ 类都是全局类。我们在前面定义的所有类都在全局作用域中，全局类具有全局作用域。

与之对应的，一个类A还可以定义在另一类B的定义中，这就是**嵌套类**结构。A类被称为B类的**内部类**，B类被称为A类的**外部类**。

以Point类和Line类为例

``` c++
class Line
{
public:
    class Point
    {
    public:
        Point(int x,int y)
        : _ix(x)
        , _iy(y)
        {}
    private:
        int _ix;
        int _iy;
    };
public:
    Line(int x1, int y1, int x2, int y2)
    : _pt1(x1,y1)
    , _pt2(x2,y2)
    {}
private: 
    Point _pt1;
    Point _pt2;
};

void test0(){
    Line ll(1,2,3,4);
    // 内部类创建对象时要加上外部类的作用域
    // 如果内部类定义在外部类的私有区域，则无法在外部类之外创建内部类对象
    Line::Point pt(8,9);

    cout << sizeof(pt) << endl;
    cout << sizeof(ll) << endl;
}
```

**Point类是定义在Line类中的内部类，无法直接创建Point对象，需要在Line类名作用域中才能创建**

``` c++
Point pt(1,2); // error
Line::Point pt2(3,4); // ok
```



A类是B类的内部类，并不代表A类的数据成员会占据B类对象的内存空间，**在存储关系上并不是嵌套的结构**。

**只有当<u>B类有A类类型的对象成员</u>时，B类对象的内存布局中才会包含A类对象（成员子对象）。**



思考，如果想要使用输出流运算符输出上述的嵌套类对象，应该怎么实现？



### 嵌套类结构的访问权限

| 访问成员的方式               | 不依赖对象直接访问 | 类名作用域访问                | 通过对象直接访问             |
| ---------------------------- | ------------------ | ----------------------------- | ---------------------------- |
| 外部类对内部类的成员进行访问 | 无                 | 内部类的静态成员+声明友元才ok | 内部类的私有成员需要声明友元 |
| 内部类对外部类的成员进行访问 | 外部类的静态成员   | 外部类的静态成员              | 即使是私有成员也ok           |

**内部类相当于是定义在外部类中的外部类的友元类**



### pimpl模式（了解）

PIMPL（Private Implementation 或 Pointer to Implementation）是**通过一个私有的成员指针，将指针所指向的类的内部实现数据进行隐藏**。PIMPL又称作“编译防火墙”，它的实现中就用到了嵌套类。

PIMPL设计模式有如下优点：

1. 提高编译速度；
2. 实现信息隐藏；
3. 减小编译依赖，可以用最小的代价平滑的升级库文件；
4. 接口与实现进行解耦；
5. 移动语义友好

实际项目的需求：希望**Line的实现全部隐藏**，在源文件中实现，再将其打包成库文件，交给第三方使用。

（1）头文件只给出接口：

``` c++
//Line.hpp
class Line{
public:
    Line(int x1, int y1, int x2, int y2);
    ~Line();
    void printLine() const;//打印Line对象的信息
private:
    class LineImpl;//类的前向声明
    LineImpl * _pimpl;
};
```

（2）在实现文件中进行具体实现，使用嵌套类的结构（LineImpl是Line的内部类，Point是LineImpl的内部类），Line类对外公布的接口都是使用LineImpl进行具体实现的。在测试文件中创建Line对象（最外层），使用Line对外提供的接口，但是不知道具体的实现。

``` c++
// LineImpl.cc
#include "Line.hpp"
#include <iostream>
using std::cout;
using std::endl;

class Line::LineImpl
{
public:
    class Point{
    public:
        Point(int x,int y)
        : _ix(x)
        , _iy(y)
        { cout << "Point(int,int)" << endl; }

        ~Point(){
            cout << "~Point()" << endl;
        }

        void print() const{
            cout << "(" << _ix
                << "," << _iy
                << ")";
        }
    private:
        int _ix;
        int _iy;
    };

public:
    LineImpl(int x1, int y1, int x2, int y2)
    : _pt1(x1,y1)
    , _pt2(x2,y2)
    {   cout << "LineImpl(int * 4)" << endl;}

    ~LineImpl(){ cout << "~LineImpl" << endl; }

    void printLine(){
        _pt1.print();
        cout << "---->";
        _pt2.print();
        cout << endl;
    }
    
private: 
    Point _pt1;
    Point _pt2;
    /* double length = 10; */
};

Line::Line(int x1,int y1,int x2,int y2)
: _pimpl(new LineImpl(x1,y1,x2,y2))
{ cout << "Line(int * 4)" << endl; }

Line::~Line(){
    cout << "~Line()" << endl;
    if(_pimpl){
        delete _pimpl;
        _pimpl = nullptr;
    }
}

void Line::printLine() const{
    _pimpl->printLine();
}

// Line.cc
void test0(){
    cout << sizeof(Line) << endl;
    Line ll(1,2,3,4);
    ll.printLine();
}
```

（3）打包库文件，将库文件和头文件交给第三方

```C++
sudo apt install build-essential
g++ -c LineImpl.cc
ar rcs libLine.a LineImpl.o

生成libLine.a库文件
编译：g++ Line.cc(测试文件) -L(加上库文件地址) -lLine(就是库文件名中的lib缩写为l，不带后缀)
此时的编译指令为 g++ Line.cc -L. -lLine
```



内存结构

<img src="./cppbase.assets/image-20231030151731561.png" alt="image-20231030151731561" style="zoom:80%;" />

**pimpl模式是一种减少代码依赖和编译时间的C++编程技巧，其基本思想是将一个外部可见类的实现细节（一般是通过私有的非虚成员）放在一个单独的实现类中，在可见类中通过一个私有指针来间接访问该类型。**

好处：

1. 实现信息隐藏；
2. 只要头文件中的接口不变，实现文件可以随意修改，修改完毕只需要将新生成的库文件交给第三方即可；
3. 可以实现库的平滑升级。



##  ==单例对象自动释放（重点*）==

在类与对象的章节，我们学习了单例模式。单例对象由静态指针 `_pInstance` 保存，最终通过手动调用 `destroy()` 函数进行释放。

**现实工作中，单例对象是需要进行自动释放。**程序在执行的过程中，需要判断有哪些地方发生了内存泄漏，此时需要工具 valgrind 的使用来确定。假设单例对象没有进行自动释放，那么 valgrind 工具会认为单例对象是内存泄漏。程序员接下来还得再次去确认到底是不是内存泄漏，增加了程序员的额外的工作。

那么如何实现单例对象的自动释放呢？

—— 看到自动就应该想到当对象被销毁时，析构函数会被自动调用。

### 方式一：利用另一个对象的生命周期管理资源

<img src="./cppbase.assets/image-20231030155337808.png" alt="image-20231030155337808" style="zoom:80%;" />

利用对象的生命周期管理资源析构函数（在析构函数中会执行`delete _p;`），当对象被销毁时会自动调用。

``` c++
class Singleton{
    // ...
    friend class AutoRelease;//友元类
    // ...
};

class AutoRelease{
public:
    AutoRelease(Singleton * p)
    : _p(p)
    { cout << "AutoRelease(Singleton*)" << endl; }

    ~AutoRelease(){
        cout << "~AutoRelease()" << endl;
        if(_p){
            delete _p;//对象销毁时自动调用，利用另一个对象的生命周期管理资源
            _p = nullptr;
        }
    }
private:
    Singleton * _p;
};

void test0(){
    AutoRelease ar(Singleton::getInstance());
    Singleton::getInstance()->print();
}

void main(){
    test0();
    // 如果局部对象ar生命周期结束了，堆上的单例对象也会被回收
    // 此时如果通过getInstance来访问这片空间，会访问到不确定的值
    Singleton::getInstance()->print(); // (随机的值,随机的值) test0结束后已经被销毁，
}
```

要注意：**如果还手动调用了Singleton类的 `destroy()` 函数，会导致double free问题，所以可以删掉 `destroy()` 函数，将回收堆上的单例对象的工作完全交给AutoRelease对象**。

上课代码：

```c++
#include <iostream>
using std::cout;
using std::endl;

class Singleton {
    Singleton(int x,int y)
    : _ix(x)
    , _iy(y)
    {
        cout << "Singleton(int,int)" << endl;
    }

    ~Singleton(){
        cout << "~Singleton()" << endl;
    }

    // C++11之后的写法
    // 表示从这个类中删除这个函数
    Singleton(const Singleton & rhs) = delete;
    Singleton & operator=(const Singleton& rhs) = delete;

public:
    static Singleton * getInstance() {
        if(_pInstance == nullptr) {
            _pInstance  = new Singleton(1,2);
        }
        return _pInstance;
    }

    static void destroy() {
        if(_pInstance) {
            delete _pInstance;
            _pInstance = nullptr;
        } 
        cout << "delete heap" << endl;
    }

    void init(int x,int y) {
        _ix = x;
        _iy = y;
    }

	void print() {
		cout << "(" << this->_ix 
            << "," << this->_iy
			<< ")" << endl;
	}

    // 声明友元
    friend class AutoRelease;

private:
	int _ix;
	int _iy;
    static Singleton * _pInstance;
};
Singleton * Singleton::_pInstance = nullptr;

class AutoRelease{
public:
    AutoRelease(Singleton * p)
    : _p(p)
    {
        cout << "AutoRelease(Singleton*)" << endl;
    }

    ~AutoRelease(){
        cout << "~AutoRelease()" << endl;
        if(_p){
            delete _p;
            _p = nullptr;
        }
    }

private:
    Singleton * _p;
};

void test0()
{
    AutoRelease ar(Singleton::getInstance());

    Singleton::getInstance()->init(100,200);
    Singleton::getInstance()->print();
    Singleton::getInstance()->print();
    Singleton::getInstance()->print();
    Singleton::getInstance()->print();

    // 如果仍然手动调用destroy会造成double free
    // destroy函数的执行过程中利用_pInstance回收堆空间
    // ar对象在栈上，栈帧结束时销毁，必然会调用，AutoRelease的析构函数，仍然会尝试回收这片堆空间
    // Singleton::destroy(); // error: double free
}

int main(void){
    test0();
    // 如果局部对象ar生命周期结束了，堆上的单例对象也被回收
    // 此时如果通过getInstance来访问这片空间，会访问到不确定的值
    // Singleton::getInstance()->print();//随机的值
    return 0;
}
```



### 方式二：嵌套类 + 静态对象

<img src="./cppbase.assets/image-20231030160919761.png" alt="image-20231030160919761" style="zoom:80%;" />

AutoRelease 类对象 `_ar` 是 Singleton 类的对象成员，创建 Singleton 对象，就会自动创建一个 AutoRelease 对象（静态区），它的成员函数可以直接访问 `_pInstance`。

``` c++
class Singleton
{
    class AutoRelease{
    public:
        AutoRelease()
        {}
        ~AutoRelease(){
          // ...
        }
    };
    // ...
private:
   // ...
    int _ix;
    int _iy;
    static Singleton * _pInstance;
    static AutoRelease _ar;
};

Singleton* Singleton::_pInstance = nullptr;
// 使用AutoReleas类的无参构造对_ar进行初始化
Singleton::AutoRelease Singleton::_ar;

void test1(){
    Singleton::getInstance()->print();
    Singleton::getInstance()->init(10,80);
    Singleton::getInstance()->print();
}
```

程序结束时会自动销毁全局静态区上的 `_ar`，调用 AutoRelease 的析构函数，在这个析构函数执行 `delete _pInstance;` 的语句，这样又会调用 Singleton 的析构函数，再调用 operator delete，回收掉堆上的单例对象。

我们利用嵌套类实现了一个比较完美的方案，不用担心手动调用了 destroy 函数。

上课代码：

```c++
#include <iostream>
using std::cout;
using std::endl;

class Singleton {
    class AutoRelease{
    public:
        AutoRelease()
        {
            cout << "AutoRelease()" << endl;
        }

        ~AutoRelease(){
            cout << "~AutoRelease()" << endl;
            if(_pInstance){
                delete _pInstance;
                _pInstance = nullptr;
            }
        }
    }; // end of class AutoRelease

    Singleton(int x,int y)
    : _ix(x)
    , _iy(y)
    {
        cout << "Singleton(int,int)" << endl;
    }

    ~Singleton(){
        cout << "~Singleton()" << endl;
    }

    // C++11之后的写法
    // 表示从这个类中删除这个函数
    Singleton(const Singleton & rhs) = delete;
    Singleton & operator=(const Singleton& rhs) = delete;

public:
    static Singleton * getInstance(){
        if(_pInstance == nullptr){
            _pInstance  = new Singleton(1,2);
        }
        return _pInstance;
    }

    static void destroy(){
        if(_pInstance){
            delete _pInstance;
            _pInstance = nullptr;
        }
        cout << "delete heap" << endl;
    }

    void init(int x,int y){
        _ix = x;
        _iy = y;
    }

    void print()
    {
        cout << "(" << this->_ix 
            << "," << this->_iy
            << ")" << endl;
    }

private:
    int _ix;
    int _iy;
    static Singleton * _pInstance;
    static AutoRelease _ar;
};

Singleton * Singleton::_pInstance = nullptr;
Singleton::AutoRelease Singleton::_ar;

void test0()
{
    Singleton::getInstance()->init(100,200);
    Singleton::getInstance()->print();
    Singleton::getInstance()->print();
    Singleton::getInstance()->print();
    Singleton::getInstance()->print();

    Singleton::destroy();
    Singleton::getInstance()->init(100,300);
    Singleton::getInstance()->print();
    Singleton::destroy();
    Singleton::destroy();
}

int main(void){
    test0();
    cout << endl;
    Singleton::getInstance()->print();
    return 0;
}
```



### 方式三：使用 atexit() 注册 destroy()

很多时候我们需要在程序退出的时候做一些诸如释放资源的操作，但程序退出的方式有很多种，比如main()函数运行结束、在程序的某个地方用exit()结束程序、用户通过Ctrl+C操作来终止程序等等，因此需要有一种**与程序退出方式无关的方法来进行程序退出时的必要处理**。

方法就是==**用atexit函数来注册程序正常终止时要被调用的函数**==（C/C++通用）。

```c++
#include <iostream>
using std::cout;
using std::endl;

void print(){
    cout << "print()" << endl;
}

void display(){
    cout << "display()" << endl;
}

void test0(){
    //先注册的后调用
    atexit(print);
    atexit(display);
}

int main(void){
    cout << "start" << endl;
    test0();
    cout << "over" << endl;
    return 0;
}
```

```
输出：
start
over
display()
print()先注册的，后调用
```

如果注册了多个函数，**==先注册的后执行==**。



atexit注册了destroy函数，相当于有了一次必然会进行的destroy（程序结束时），即使手动调用了destroy，因为安全回收的机制，也不会有问题。

``` c++
class Singleton
{
public:
    static Singleton * getInstance(){
        if(_pInstance == nullptr){
            atexit(destroy);
            _pInstance = new Singleton(1,2);
        }
        return _pInstance;
    }
    //...
}；
```

但是还遗留了一个问题，就是以上几种方式都无法解决**==多线程安全==**问题。

以方式三为例，当**多个线程同时进入 if 语句**时，会造成**<u>单例对象被创建出多个</u>**，但是最终**只有一个地址值会由 `_pInstance;` 指针保存**，因此造成**内存泄漏**。

### 多线程安全问题

- 可以使用**==饿汉式==**解决，但同时也可能带来内存压力（即使不用单例对象，也会被创建）


``` c++
// 对于_pInstance的初始化有两种方式

// 饱汉式（懒汉式）—— 懒加载，不使用到该对象，就不会创建
Singleton* Singleton::_pInstance = nullptr; 

// 饿汉式 —— 最开始就创建（即使不使用这个单例对象）内存压力
Singleton* Singleton::_pInstance = getInstance();
```

代码：

```c++
#include <iostream>
using std::cout;
using std::endl;

class Singleton {
    Singleton(int x,int y)
    : _ix(x)
    , _iy(y)
    {
        cout << "Singleton(int,int)" << endl;
    }

    ~Singleton(){
        cout << "~Singleton()" << endl;
    }

    // C++11之后的写法
    // 表示从这个类中删除这个函数
    Singleton(const Singleton & rhs) = delete;
    Singleton & operator=(const Singleton& rhs) = delete;

public:
    static Singleton * getInstance(){
        if(_pInstance == nullptr){
            atexit(destroy);
            _pInstance  = new Singleton(1,2);
        }
        return _pInstance;
    }

    void init(int x,int y){
        _ix = x;
        _iy = y;
    }
    static void destroy(){
        if(_pInstance){
            delete _pInstance;
            _pInstance = nullptr;
        }
    }

	void print()
	{
		cout << "(" << this->_ix 
            << "," << this->_iy
			<< ")" << endl;
	}
private:
	int _ix;
	int _iy;
    static Singleton * _pInstance;
};
// 饱汉式，初始化成空指针，懒加载
// Singleton * Singleton::_pInstance = nullptr; // 无法解决多线程安全问题
// 饿汉式，初始化时就创建出单例对象
Singleton * Singleton::_pInstance = getInstance(); // 多线程安全，存在内存压力

void test0()
{
    Singleton::getInstance()->print();
    Singleton::getInstance()->print();
    Singleton::getInstance()->print();
    Singleton::getInstance()->print();
    Singleton::destroy();
    cout << endl;
    Singleton::getInstance()->print();
    Singleton::getInstance()->print();
}

int main(void){
    test0();
    return 0;
}
```



### 方式四：atexit + pthread_once（多线程安全）

**==Linux平台==**可以使用的方法（能够保证创建单例对象时的**==多线程安全==**）

==**`pthread_once()` 函数可以确保初始化代码只会执行一次**==。

传给 pthread_once() 函数的<u>第一个参数比较特殊，形式固定</u>；第二个参数需要是一个<u>**静态函数指针**</u>。

![image-20240304162141278](./cppbase.assets/image-20240304162141278.png)

``` c++
class Singleton{   
public:
    static Singleton * getInstance(){
        pthread_once(&_once,init_r);
        return _pInstance;
    }

    static void init_r(){
        _pInstance = new Singleton(1,2);
        atexit(destroy);
    }
    //...
private:
	int _ix;
    int _iy;
    static Singleton * _pInstance;
    static pthread_once_t _once;
};
Singleton* Singleton::_pInstance = nullptr;
pthread_once_t Singleton::_once = PTHREAD_ONCE_INIT;
```

**注意：**因为初始化（创建堆对象）的语句之后被执行一次，所以不能手动调用 `destroy()` 函数，同时因为会使用atexit注册 `destroy()` 函数实现资源回收，所以也不能将 `destroy()` 删掉，应该将其私有，避免在类外手动调用。



##  ==std::string的底层实现*==

我们都知道，std::string的一些基本功能和用法了，但它底层到底是如何实现的呢？其实在std::string的历史中，出现过几种不同的方式。

我们可以从一个简单的问题来探索，一个std::string对象占据的内存空间有多大，即sizeof(std::string)的值为多大？如果我们在不同的编译器（VC++, GCC, Clang++）上去测试，可能会发现其值并不相同；即使是GCC，不同的版本，获取的值也是不同的。

虽然历史上的实现有多种，但基本上有三种方式：

- **Eager Copy(深拷贝)**
- **COW(Copy-On-Write 写时复制)**
- **SSO(Short String Optimization 短字符串优化)**

**std::string 的底层实现是一个高频考点**，虽然目前std::string是根据SSO的思想实现的，但是我们最好能够掌握其发展过程中的不同设计思想，在回答时会是一个非常精彩的加分项。

### 深拷贝（Eager Copy）

首先，最简单的就是深拷贝。无论什么情况，都是采用**==拷贝字符串内容==**的方式解决，这也是我们之前已经实现过的方式。这种实现方式，在不需要改变字符串内容时，对字符串进行频繁复制，效率比较低下。所以需要对其实现进行优化，之后便出现了下面的 COW 的实现方式。

``` c++
// 如果string的实现直接用深拷贝
string str1("hello,world");
string str2 = str1;
```

如上，str2 保存的字符串内容与 str1 完全相同，但是根据深拷贝的思想，一定要重新申请空间、复制内容，这样效率较低、开销较大。

### 写时复制（cow）

#### 原理探究：当字符串对象进行==复制控制==时，可以优化为==指向<u>同一个堆空间</u>的字符串==。

**Q1: 接下来的问题就是<u>*何时回收堆空间的字符串内容呢*</u>？**  

- 引用计数 refcount 当字符串对象进行**复制**操作时，**引用计数+1**；
- 当字符串对象被**销毁**时，**引用计数-1**；
- 只有当**引用计数减为0**时，才真正**回收**堆空间上字符串。

<img src="./cppbase.assets/image-20231030171818083.png" alt="image-20231030171818083" style="zoom: 67%;" />

**Q2: <u>*引用计数应该放到哪里*</u>？**

<img src="./cppbase.assets/image-20231030180507191.png" alt="image-20231030180507191" style="zoom: 80%;" />

<img src="./cppbase.assets/image-20231030180549996.png" alt="image-20231030180549996" style="zoom:80%;" />

<img src="./cppbase.assets/image-20231030180607030.png" alt="image-20231030180607030" style="zoom:80%;" />

方案三可行，还可以优化一下

按常规的思路，需要使用两次new表达式（字符串、引用计数）；可以优化成只用一次new表达式，因为申请堆空间的行为一定会涉及系统调用，程序员要尽量少使用系统调用，提高程序的执行效率。

优化方案：只new一次

**==引用计数存放的位置：==**字符串的前四字节（一个int）。`char * _pstr` 指向的位置是字符串的首地址，而不是整段地址的首地址。

<img src="./cppbase.assets/image-20231030180657333.png" alt="image-20231030180657333" style="zoom:80%;" />

引用计数减到1，才真正回收堆空间。

<img src="./cppbase.assets/image-20231030180747854.png" alt="image-20231030180747854" style="zoom:80%;" />

#### CowString代码初步实现

根据**写时复制**的思想来模拟字符串对象的实现，这是一个非常有难度的任务（源码级），理解了COW的思想后可以尝试实现一下。



### 短字符串优化（SSO）

**==当字符串的字符数小于等于15时，buffer直接存放整个字符串；当字符串的字符数大于15时，buffer 存放的就是一个<u>*指针，指向堆空间的区域*</u>==。**这样做的好处是，当字符串较小时，直接拷贝字符串，放在string内部，不用获取堆空间，开销小。

<img src="./cppbase.assets/image-20231101084127746.png" alt="image-20231101084127746" style="zoom:80%;" />

```C++
class string {
	union Buffer{
		char * _pointer;
		char _local[16];
	};
	
	size_t _size;
	size_t _capacity;
    Buffer _buffer;
};
```



### 最佳策略

Facebook(Meta) 提出的最佳策略，**将三者进行结合**：

因为以上三种方式，都不能解决所有可能遇到的字符串的情况，各有所长，又各有缺陷。综合考虑所有情况之后，facebook开源的**folly库**中，实现了一个fbstring, 它==**根据字符串的不同长度使用不同的拷贝策略**， 最终每个fbstring对象占据的空间大小都是**24字节**==。

1. 很短的（0~22）字符串用**短字符串优化(SSO)**，23字节表示字符串（包括'\0'）,1字节表示长度。23+1=24

2. 中等长度的（23~255）字符串用**深拷贝(eager copy)**，8字节字符串指针，8字节size，8字节capacity。8+8+8=24
3. 很长的（大于255）字符串用**写时复制(COW)**，8字节指针（字符串和引用计数），8字节size，8字节capacity。8+8+8=24



### 线程安全性

两个线程同时对同一个字符串进行操作的话，是不可能线程安全的，出于性能考虑，**==C++并没有为string实现线程安全==**，毕竟不是所有程序都要用到多线程。但是两个线程同时对独立的两个 string 操作时，必须是安全的。COW技术实现这一点是通过**==原子的==**对引用计数进行+1或-1操作。

CPU的**原子操作**虽然比mutex锁好多了，但是仍然会带来性能损失，原因如下：

1. 阻止了CPU的乱性执行

2. 两个CPU对同一个地址进行原子操作，会导致cache失效，从而重新从内存中读数据

3. 系统通常会lock住比目标地址更大的一片区域，影响逻辑上不相关的地址访问

这也是在多核时代，各大编译器厂商都选择了SSO实现的原因吧。



# 第六章 关联式容器

学到这里，我们可以提前学习一些STL的内容了，以帮助我们完成作业。本章我们介绍两个容器set、map，它们属于STL中的关联式容器。

## set

### set的构造

包含在头文件`<set>`，打开C++参考文档，主要关注这样的几个构造函数：

1. 无参构造
2. 迭代器方式进行构造，传入一个first迭代器，传入一个last迭代器
3. 拷贝构造
4. 标准初始化列表（大括号的形式）

![image-20231031160917134](./cppbase.assets/image-20231031160917134.png)

``` c++
set<int> number;
set<int> number2 = {1,3,9,8,9};
```



**==set的特征==**：

1. set中存放的元素是==唯一==的，不能重复；

2. 默认情况下，会按照元素进行==升序==排列；




### set的查找操作

`count:` 输入一个值，在set中查找，如果有就返回1，没有就返回0。

`find:` 输入一个值，在set中进行查找，如果找到，就返回这个元素相应的迭代器。若找不到，则返回end()获取的迭代器。

<img src="./cppbase.assets/image-20231031162032219.png" alt="image-20231031162032219" style="zoom:80%;" />

<img src="./cppbase.assets/image-20231031162137072.png" alt="image-20231031162137072" style="zoom:80%;" />



### set的插入操作

**==参数是一个key，返回的值是一个pair类型==**（包含一个迭代器和一个bool值）

![image-20231031163352909](./cppbase.assets/image-20231031163352909.png)

> pair可以存储两种不同类型的对象，C++中结构体已经演变为了类
>
> 重点关注：pair的对象成员如何访问
>
> ```C++
> std::cout << rem << "(" << pair.first << ", " << pair.second << ")\n";
> ```
>
> 试验pair的使用：
>
> ```c++
> #include <utility>
> void test1(){
>     pair<int,string> num = {1,"wangdao"};
>     cout << num.first << ":" << num.second << endl;
> }
> ```

#### 对set进行插入

- 插入单个元素：


``` c++
pair<set<int>::iterator,bool> ret = number.insert(8);
if(ret.second){
    cout << "该元素插入成功:" << *(ret.first) << endl;
}else{
    cout << "该元素插入失败，表明该元素已存在" << endl;
}
```

- 插入多个元素：


``` c++
int arr[5] = {18,41,35,2,99};
number.insert(arr,arr + 5); //思考，如果想要插入arr的全部元素，此处应该是arr + 5 还是 arr + 4 ?
```



注意：

- **set 容器==不支持下标访问==**，因为没有 operator[] 重载函数

- **==不能通过 set 的迭代器直接修改key值==**，set 的底层实现是红黑树，结构稳定，不允许直接修改。



##  map

### map的构造

map中存放的元素的类型是**pair类型（键值对）**，构造map需要关注三种方式，也可以把它们结合到一起。如下：

```` c++
void test0(){
    map<int,int> number1;
    
	map<int,string> number2 = {
        {1,"hello"},
        {2,"world"},
        {3,"wangdao"},
        
        pair<int,string>(4,"hubei"),
        pair<int,string>(5,"wangdao"),
        
        make_pair(9,"shenzhen"),
        make_pair(3,"beijing"),
        make_pair(6,"shanghai")
    }; 
}
````



**==使用迭代器方式遍历map==**：注意访问map的元素pair的内容时的写法

``` c++
map<int,string>::iterator it = number.begin();
while(it != number.end()){
    cout << (*it).first << " " << it->second << endl;
    ++it;
}
cout << endl;
```



**==map的特征==**：

- ==元素唯一==：创建map对象时，舍弃了一些元素，==key值相同的元素被舍弃==。key不同，即使value相同也能保留。
- 默认以key值为参考进行==升序==排列。




### map的查找操作

根据 key 值在 map 中进行查找

`count:` 函数的返回值：如果找到返回1，如果没找到返回0（size_t类型）。

`find:` 函数的返回值：如果找到返回相应元素的迭代器，如果没找到返回 end() 的结果。



### map的插入操作

- 插入单个元素，此时insert函数的返回值是一个pair（第一个对象成员是map元素相应的迭代器，第二个对象成员是bool值）


``` c++
pair<map<int,string>::iterator,bool> ret = number.insert(pair<int,string>(7,"nanjing"));
if(ret.second){
    cout << "该元素插入成功" << endl;
    //ret.first取出来的是指向map元素（pair<int,string>）的迭代器
    //再用箭头运算符访问到的是int和string的内容
    cout << ret.first->first << " : " << ret.first->second << endl;
}else{
    cout << "该元素插入失败" << endl;
}
cout << endl;
```

- 插入一组元素


``` c++
//再创建一个map
map<int,string> number2 = {{1,"beijing"},{18,"shanghai"}};

//迭代器方式
number2.insert(number.begin(),number.end());

//列表方式
cout << endl;
number2.insert({{4,"guangzhou"},{22,"hello"}});
```



### map的下标操作

map支持下标操作

1. map下标操作返回的是map中元素（pair）的value
2. 下标访问运算符中的值代表key，而不是传统意义上的下标
3. 如果进行下标操作时下标值传入一个**==不存在的key==**，那么会==**将这个key和空的value插入到map中**==
4. 下标访问可以进行写操作



# 第七章 继承

## 继承的基本概念

在学习类和对象时，我们知道对象是基本，我们从对象上抽象出类。但是，世界可并不是一层对象一层类那么简单，对象抽象出类，在类的基础上可以再进行抽象，抽象出更高层次的类。

而 C++ 中模拟这种结构发展的方式就是继承，它也是代码重用的方式之一。通过继承，我们可以用原有类型来定义一个新类型，定义的新类型既包含了原有类型的成员，也能自己添加新的成员，而不用将原有类的内容重新书写一遍。原有类型称为“基类”或“父类”，在它的基础上建立的类称为“派生类”或“子类”。

总的来说，定义派生类的需求一般是：

1. 复用原有代码的功能；

2. 添加新的成员；

3. 实现新的功能

定义派生类时，需要要在派生类的类派生列表中明确的指出它是从哪个基类继承而来的。

```C++
class 基类
{};

class 派生类
: public/protected/private 基类
{};
```

如上述代码所示，有三种继承方式，其“继承效果”如图：

<img src="./cppbase.assets/image-20231031212558160.png" alt="image-20231031212558160" style="zoom:80%;" />



**定义一个派生类的过程：**

1. 吸收基类的成员
2. 添加新的成员（非必须）
3. 隐藏基类的成员（非必须）

``` c++
class Point3D
: public Point // 公有继承
{
public:
    Point3D(int x, int y, int z)
    : Point(x,y)
    , _iz(z)
    {
        cout << "Point3D(int*3)" << endl;
    }
	// 添加了新的成员函数
    void display() const{
       	print();
        cout << _z << endl;
    }
private:
    // 添加新的数据成员
    int _iz;
};
```

如果定义一个派生类只写了继承关系，没有写任何的自己的内容，那么也会吸收基类的成员，这个情况叫做**空派生类**（其目的是在特定的场景建立继承关系，为将来的拓展留出空间）。



### **三种继承方式的访问权限**

<img src="./cppbase.assets/image-20231031212909599.png" alt="image-20231031212909599" style="zoom:80%;" />

**总结**：派生类的访问权限如下：

1. 不管什么继承方式，派生类内部都不能访问基类的私有成员；
2. 不管什么继承方式，派生类内部除了基类的私有成员不可以访问，其他的都可以访问；
3. 不管什么继承方式，派生类对象在类外除了公有继承基类中的公有成员可以访问外，其他的都不能访问。

**(记忆：1.私有的成员在类外无法直接访问； 2.继承方式和基类成员访问权限做交集)**



根据上面的总结，很容易感受到公有继承和另外两种继承方式的区别，但是**==保护继承(protected)和私有继承(private)之间有什么区别==**呢？—— 如果再往下派生一层，试着在最底层的派生类中访问顶层基类的成员，看看效果。

以**三层继承**为例：

如果中间层采用**保护继承**的方式继承顶层基类，那么在底层派生类中也能访问到顶层基类的**公有成员**和**保护成员**。

如果中间层采用**私有继承**的方式继承顶层基类，那么底层派生类中对顶层基类的<u>任何成员都无法访问</u>了。



**私有继承的特性：**

==在**多层继承**的关系中，如果有一层采用了**私有继承**的方式，那么再往下进行派生的类就没法访问更上层的**基类**的成员了。==

``` c++
class A
{ 
public: 
	int a;
};

class B
: private A
{};

class C
: private B
{
    void func(){
        a;//error，无法访问a
    }
};
```



**常考题总结：**

**Q1：派生类在==类之外==对于基类成员的访问 ，具有什么样的限制？**

==只有公有继承自基类的公有成员，可以通过派生类对象直接访问==，其他情况一律都不可以进行访问。

**Q2：派生类在==类内部==对于基类成员的访问 ，具有什么样的限制？**

对于基类的私有成员，不管以哪种方式继承，在派生类内部都不能访问；

==对于基类的**非私有成员**，不管以哪种方式继承，在派生类内部都可以访问==。

**Q3：保护继承和私有继承的区别？**

如果继承层次中都采用的是==**保护继承**，任意层次都可以访问顶层基类的非私有成员==；

但如果采用私有继承之后，这种特性会被打断。

—— 公有继承被称为接口继承，保护继承、私有继承称为实现继承。



### 继承关系的局限性

**创建、销毁的方式**不能被继承 —— 构造、析构

**复制控制的方式**不能被继承 —— 拷贝构造、赋值运算符函数

**空间分配的方式**不能被继承 —— operator new、operator delete

**友元**不能被继承（友元破坏了封装性，为了降低影响，不允许继承）



##  单继承下派生类对象的创建和销毁

### 简单的单继承结构

有这样一种说法：创建派生类对象时，先调用基类构造函数，再调用派生类构造函数，对吗？

==错误，创建派生类对象，一定会<u>*先调用**派生类**的构造函数，在此过程中会先去调用**基类**的构造函数*</u>==。



- ==**创建派生类对象时调用基类构造的机制**==

  1. 当派生类中**没有<u>显式调用基类构造函数</u>**时，**默认会调用==基类的默认无参构造==**；
  
     此时如果**基类中没有默认无参构造**，就直接**不允许派生类对象的创建**。
  
  2. 当派生类对象调用基类构造时，希望**使用<u>非默认</u>的基类构造函数**，==必须**显式**地在初始化列表中写出==。
  
- ==**派生类对象的销毁**==

  当**派生类析构函数**==执行完毕之后==，会**自动调用基类析构函数**，完成基类部分的销毁。



**记忆：==创建一个对象，一定会马上调用自己的构造函数；一个对象被销毁，也一定会马上调用自己的析构函数==。**



> 示例：
>
> 1. 当派生类中**没有显式调用基类构造函数**时，**默认会调用基类的默认无参构造**；
>
>    ```c++
>    class Base {
>    public:
>    	Base(){ cout << "Base()" << endl; }
>    private:
>    	long _base;
>    };
>    
>    class Derived
>    : public Base 
>    {
>    public:
>    	Derived(long derived)
>        // : Base() // 自动调用Base的默认无参构造
>    	: _derived(derived)
>    	{ cout << "Derived(long)" << endl; }
>        
>    	long _derived;
>    };
>    
>    void test() {
>    	Derived d(1);
>    }
>    ```
>
>    <img src="./cppbase.assets/undefined202403181523373.png" alt="undefined202403181523373" style="zoom: 80%;" />
>
> 2. 此时如果**基类中没有默认无参构造**，就直接**不允许派生类对象的创建**；
>
>    <img src="./cppbase.assets/undefined202403181525812.png" alt="undefined202403181525812" style="zoom:50%;" />
>
> 3. 当派生类对象调用基类构造时，希望使用非默认的基类构造函数，必须**显式**地在初始化列表中写出。
>
>    ```c++
>    class Base {
>    public:
>    	Base(long base){ cout << "Base(long)" << endl; }
>    private:
>    	long _base;
>    };
>                                                                                  
>    class Derived
>    : public Base 
>    {
>    public:
>        Derived(long base, long derived)
>        : Base(base) // 显式调用基类的构造函数
>    	，_derived(derived)
>    	{ cout << "Derived(long)" << endl; }
>    private:   
>    	long _derived;
>    };
>                                                                                  
>    void test() {
>    	Derived d; // error
>    }
>    ```
>
> 注意与对象成员的初始化做区分。



### 当派生类对象中包含对象成员

**在派生类的构造函数中**：

- 初始化列表中==显式调用**基类**的构造==，写的是**基类**的==类名`Base`==；

- 初始化列表中==显式调用**对象成员**的构造函数==，写的是**对象成员**的==名字`_t`==。


``` c++
class Test{
public:
    Test(long test)
    : _test(test)
    { cout << "Test()" << endl; }
    ~Test(){ cout << "~Test()" << endl; }
private:
    long _test;
};

class Derived
: public Base
{
public:
    Derived(long base,long test,long b2,long derived)
    : Base(base)// 创建基类子对象 显式调用基类的构造函数 写类名Base
    , _t(test)	// 创建Test类的成员子对象 显式调用对象成员的构造函数 写对象成员的名字_t
    , _b(b2)	// 创建Base类的成员子对象
    , _derived(derived)
    {
        cout << "Derived()" << endl;
    }

    ~Derived(){
        cout << "~Derived()" << endl;
    }
    
private:
    Test _t; //对象成员
    Base _b;
    long _derived;
};
```

<img src="./cppbase.assets/undefined202403181558960.png" alt="undefined202403181558960" style="zoom:80%;" />

- 思考： 如果再给派生类中加上一个基类的对象成员，派生类的构造函数应该怎么写呢？


<img src="./cppbase.assets/undefined202403181606557.png" alt="undefined202403181606557" style="zoom:80%;" />

创建一个派生类对象时，会马上调用自己（派生类）的构造函数，在此过程中，还是会先调用基类的构造函数创建基类子对象，然后==根据**对象成员的<u>*声明顺序*</u>**==去调用对象成员的构造函数，创建出成员子对象；

一个派生类对象销毁时，调用自己（派生类）的析构函数，析构函数执行完后，按照==对象成员的<u>声明顺序的***逆序***</u>==去调用对象成员的析构函数，最后调用基类的析构函数。



### 对基类成员的隐藏

#### 基类数据成员的隐藏

==派生类中定义了和基类的数据成员**同名**的数据成员，就会<u>对基类的这个数据成员形成**隐藏**</u>，无法直接访问基类的这个数据成员==

隐藏不代表改变了基类的这个数据成员

如果一定要访问基类的这个数据成员，需要**==加上作用域==**，但是这种写法不符合面向对象的原则，不推荐实际使用。

```` c++
class Base{
public:
    Base(long x)
    : _base(x)
    {
        cout << "Base()" << endl;
    }
    
    void print() const{
        cout << "Base::_base:" << _base << endl;
        cout << "Base::_data:" << _data  << endl;
    }
    
    long _data = 100;
private:
    long _base;
};

class Derived
: public Base
{
public:
    Derived(long base,long derived)
    : Base(base) // 创建基类子对象
    , _derived(derived)
    {
        cout << "Derived()" << endl;
    }
    
    long _data = 19;
private:
    long _derived;

};

void test0() {
    Derived dd(1,2);
    cout << dd._data << endl;
    cout << dd.Base::_data << endl;
}
````



#### 基类成员函数的隐藏

==当派生类定义了与基类**同名**的成员函数时，只要**名字相同**，即使参数列表不同，也<u>**只能看到派生类部分，无法调用基类的同名函数**</u>==。

看一个例子：

- Base中定义一个不传参的 `print()` 函数，Derived类中不定义 `print()` 函数，Derived对象调用 `print()`，输出的基类的 `_data`。


``` c++
#include <iostream>
using std::cout;
using std::endl;

class Base{
public:
    Base(long x)
    : _base(x)
    {
        cout << "Base()" << endl;
    }

    void print() const {
        cout << "Base::_base:" << _base << endl;
        cout << "Base::_data:" << _data  << endl;
    }
    
    long _data = 100;
private:
    long _base;
};

class Derived
: public Base
{
public:
    Derived(long base,long derived)
    : Base(base) // 创建基类子对象
    , _derived(derived)
    {
        cout << "Derived()" << endl;
    }

    long _data = 19;
private:
    long _derived;

};

void test0(){
    Derived dd(1,2);
    //当派生类定义了与基类同名的数据成员
    //那么对基类的这个数据成员形成了隐藏
    cout << dd._data << endl;
    cout << dd.Base::_data << endl;
    dd.print();
}

int main(void){
    test0();
    return 0;
}
```

```
输出：
Base()
Derived()
19
100
Base::_base:1
Base::_data:100
```

<img src="https://bray07.oss-cn-beijing.aliyuncs.com/undefined202403181624245.png" alt="image-20240318162430164" style="zoom:67%;" />

- Derived类中定义一个 `print()` 函数，再通过Derived对象调用 `print()` 函数会调用到自己的 `print()`。


```c++
#include <iostream>
using std::cout;
using std::endl;

class Base{
public:
    Base(long x)
    : _base(x)
    {
        cout << "Base()" << endl;
    }

    void print() const{
        cout << "Base::_base:" << _base << endl;
        cout << "Base::_data:" << _data  << endl;
    }
    
    long _data = 100;
private:
    long _base;
};

class Derived
: public Base
{
public:
    Derived(long base,long derived)
    : Base(base) // 创建基类子对象
    , _derived(derived)
    {
        cout << "Derived()" << endl;
    }

    void print() const{
        cout << "Derived::_data:" << _data  << endl;
    }

    long _data = 19;
private:
    long _derived;
};

void test0(){
    Derived dd(1,2);
    //当派生类定义了与基类同名的数据成员
    //那么对基类的这个数据成员形成了隐藏
    cout << dd._data << endl;
    cout << dd.Base::_data << endl;
    dd.print();
}

int main(void){
    test0();
    return 0;
}
```

```
输出：
Base()
Derived()
19
100
Derived::_data:19
```

Derived中 `print()` 函数需要传入一个int型参数。

````c++
void print(int x) const {
    cout << "Derived::_derived:" << _derived << endl;
    cout << "Derived::_data:" << _data  << endl;
}
````

使用Derived对象调用 `print()` 时，只能通过传入一个int参数的形式去调用，说明Base类中的 `print()` 函数也发生了隐藏。

如果一定要调用基类的这个成员函数，需要加上**作用域**，但是这种写法不符合面向对象的原则，不推荐实际使用。

```c++
dd.print(1);
dd.Base::print();
```



##  多继承

C++ 除了支持单继承外，还支持多重继承。那为什么要引入多重继承呢？其实是因为在客观现实世界中，我们经常碰到一个人身兼数职的情况，如在学校里，一个同学可能既是一个班的班长，又是学生中某个部门的部长；在创业公司中，某人既是软件研发部的 CTO ，又是财务部的 CFO ；一个人既是程序员，又是段子手。诸如此类的情况出现时，单一继承解决不了问题，就可以采用多基继承了。

继承关系本质上是一个IS A的关系。

<img src="./cppbase.assets/image-20231101091031802-16988202464701.png" alt="image-20231101091031802" style="zoom:80%;" />

### 多重继承的派生类对象的构造和析构

多继承的定义方式

``` c++
class A
{
public:
    A(){ cout << "A()" << endl; }
    ~A(){ cout << "~A()" << endl; }
    void print() const{
        cout << "A::print()" << endl;
    }
};

class B
{
public:
    B(){ cout << "B()" << endl; }
    ~B(){ cout << "~B()" << endl; }
    void print() const{
        cout << "B::print()" << endl;
    }
};

class C
{
public:
    C(){cout << "C()" << endl; }
    ~C(){ cout << "~C()" << endl; }
    void print() const{
        cout << "C::print()" << endl;
    }
};

class D
: public A,B,C // D类公有继承了A类，但是对B/C类采用的默认的继承方式是private
{
public:
    D(){ cout << "D()" << endl; }
    ~D(){ cout << "~D()" << endl; }
    void print() const{
        cout << "D::print()" << endl;
    }
};
```

如果这样定义，那么D类公有继承了A类，但是对B/C类采用的默认的继承方式是private。

如果想要公有继承A/B/C三个类：

``` c++
class D
: public A
, public B
, public C // 公有继承A/B/C三个类
{
public:
    D(){ cout << "D()" << endl; }
    ~D(){ cout << "~D()" << endl; }
    void print() const{
        cout << "D::print()" << endl;
    }
};
```



- **此结构下创建D类对象时，这四个类的构造函数调用顺序如何？**

  马上调用D类的构造函数，在此过程中会**根据==继承的声明顺序==**，依次调用A/B/C的构造函数，创建出这三个类的基类子对象

- **D类对象销毁时，这四个类的析构函数调用顺序如何？**

  马上调用D类的析构函数，析构函数执行完后，**按照继承的声明顺序的==逆序==**，依次调用A/B/C的析构函数



### 多重继承可能引发的问题

#### 成员名访问冲突的二义性

<img src="./cppbase.assets/image-20231102131820080.png" alt="image-20231102131820080" style="zoom:80%;" />



解决成员名访问冲突的方法：加类作用域（不推荐）

同时，==如果D类中定义了**同名**的成员，可以对基类的这些成员造成**隐藏**效果==，那么就可以直接通过成员名进行访问。

``` c++
D d;
d.A::print();
d.B::print();
d.C::print();
d.print();
```



#### 存储二义性的问题（重要）

菱形继承结构：

```c++
class A
{
public:
    void print() const{
        cout << "A::print()" << endl;
    }
    double _a;
};

class B
: public A
{
public:
    double _b;
};

class C
: public A
{
public:
    double _c;
};

class D
: public B
, public C
{
public:
    double _d;
};
```

继承图：

<img src="./cppbase.assets/image-20231102131853385.png" alt="image-20231102131853385" style="zoom:80%;" />

内存布局：

<img src="./cppbase.assets/image-20231102161311201.png" alt="image-20231102161311201" style="zoom:67%;" />

```c++
#include <iostream>
using std::cout;
using std::endl;
class A
{
public:
    void print() const{
        cout << "A::print():" << _a  << endl;
    }
    double _a = 10.1;
};

class B
: public A
{
public:
    double _b;
};

class C
: public A
{
public:
    double _c;
};

class D
: public B
, public C
{
public:
    // void print() const{
    //     cout << "D::print()"  << endl;
    // }
    double _d;
};

void test0(){
    cout << sizeof(A) << endl;
    cout << sizeof(B) << endl;
    cout << sizeof(C) << endl;
    cout << sizeof(D) << endl;

    D d;
    // d.print();       // error D类中添加print()函数可以覆盖，就不会报错了
    // d.A::print();    // error
    d.B::print();       // 不推荐
    d.C::print();       // 不推荐
}

int main(void){
    test0();
    return 0;
}
```

菱形继承情况下，D类对象的创建会生成一个B类子对象，其中包含一个A类子对象；还会生成一个C类子对象，其中也包含一个A类子对象。所以D类对象的内存布局中有多个A类子对象，访问继承自A的成员时会发生**二义性**。因为编译器需要通过基类子对象去调用，但是不知道应该调用哪个基类子对象的成员函数。

当然，D类如果再写一个同名成员函数，会发生隐藏。

**解决存储二义性的方法：中间层的基类采用==虚继承==方式解决存储二义性**。

``` c++
class A
{
public:
    void print() const{
        cout << "A::print()" << endl;
    }
    double _a;
};

class B
: virtual public A
{
public:
    double _b;
};

class C
: virtual public A
{
public:
    double _c;
};

class D
: public B
, public C
{
public:
    double _d;
};
```

继承图：

<img src="./cppbase.assets/image-20231102162110832.png" alt="image-20231102162110832" style="zoom:67%;" />

采用虚拟继承的方式处理菱形继承问题，实际上**==改变了派生类的内存布局==**。B类和C类对象的内存布局中多出一个**==虚基类指针==**，位于所占内存空间的起始位置，同时继承自A类的内容被放在了这片空间的最后位置。D类对象中只会有一份A类的基类子对象。

<img src="./cppbase.assets/image-20231102162130028.png" alt="image-20231102162130028" style="zoom: 80%;" />

通过VS可以验证，查看D类的布局：`/d1reportAllClassLayout`

<img src="./cppbase.assets/image-20231102162819551.png" alt="image-20231102162819551" style="zoom:67%;" />

<img src="./cppbase.assets/image-20231102162736728.png" alt="image-20231102162736728" style="zoom: 80%;" />

验证得到的结果：

<img src="./cppbase.assets/image-20231102162533680.png" alt="image-20231102162533680" style="zoom: 67%;" />



##  基类与派生类之间的转换

**一般情况下，基类对象占据的空间小于派生类。**

（空类继承时，有可能相等，但是这是占位机制的具体实现，各个平台的结果也不统一，不用太在意）

1：可否把一个基类对象赋值给一个派生类对象？不行
	  可否把一个派生类对象赋值给一个基类对象？可以

2：可否将一个基类指针指向一个派生类对象？可以
	  可否将一个派生类指针指向一个基类对象？不行

3：可否将一个基类引用绑定一个派生类对象？可以
	  可否将一个派生类引用绑定一个基类对象？不行

``` c++
Base base;
Derived d1;

base = d1; // ok
d1 = base; // error

Base * pbase = &d1; // ok
Derived * pderived = &base // error
    
Base & rbase = d1; // ok
Derived & rderived = base; // error
```

以上三个ok的操作，叫做**向上转型**（往基类方向就是向上），向上转型是可行的；

**向下转型**有风险（如下）

<img src="./cppbase.assets/image-20231102164339873.png" alt="image-20231102164339873" style="zoom:80%;" />

==Base类的指针指向Derived类的对象，d1对象中存在一个Base类的基类子对象，这个Base类指针所能操纵只有**继承自Base类的部分**；==

==Derived类的指针指向Base对象，除了操纵Base对象的空间，还需要操纵一片空间，只能是**非法空间**，所以会报错。==



**有些场景下，向下转型是合理的，可以使用`dynamic_cast`来进行转换，如果属于合理情况，可以转换成功。**

``` c++
Base base;
Derived d1;
Base * pbase = &d1;//基类指针指向派生类对象

// 向下转型
Derived * pd = dynamic_cast<Derived*>(pbase);//基类指针转为派生类指针
if(pd){
    cout << "转换成功" << endl;
    pd->display();
}else{
    cout << "转换失败" << endl;
}
```

这里可以转换成功，因为pbase本身就是指向一个Derived对象

<img src="./cppbase.assets/undefined202403181753719.png" alt="image-20240318175300618" style="zoom:67%;" />

```c++
#include <iostream>
using std::cout;
using std::endl;

class Base {
public:
    Base(long base)
    : _base(base)
    { cout << "Base()" << endl; }

    virtual void display(){
        cout << "Base::display()" << endl;
    }

    ~Base(){ cout << "~Base()" << endl; }
private:
    long _base = 10;
};

class Derived
: public Base 
{
public:
    Derived(long base,long derived)
    : Base(base) // 显式调用基类的构造函数
    , _derived(derived)
    { cout << "Derived(long)" << endl; }

    ~Derived(){ cout << "~Derived()" << endl; }

    long _derived;
};

void test0(){
    Base base(1);
    Derived d1(2,3);
    Derived d2(6,8);

    // d1 = d2;
    base = d1; // ok
    // d1 = base; // error

    //基类指针指向派生类对象
    Base * pbase = &d1; // ok
    // Derived * pderived = &base; // error

    Base & rbase = d1; // ok
    // Derived & rderived = base; // error
}

void test1(){
    Base base(1);
    Derived d1(2,3);

    Base * pbase = &d1;

    // 不合理的转换 因为pbase本身是指向一个Base对象
    Base * pbase = &base;

    // 向下转型
    // 如果是合理的转换，能够成功转换成一个Derived*，并使用这个指针去访问成员
    // 如果是不合理的转换，会返回一个空指针
    Derived * pd = dynamic_cast<Derived*>(pbase);
    if(pd){
        cout << "转换成功" << endl;
        pd->display();
    }else{
        cout << "转换失败" << endl;
    }
}

int main(void){
    test1();
    return 0;
}
```



补充：基类对象和派生类对象之间的转换没有太大的意义，基类指针指向派生类对象（基类引用绑定派生类对象）重点掌握，只能访问到基类的部分。

```c++
void test0(){
    Base base(1);
    Derived derived1(2,3);
    Derived derived2(6,8);

    base = derived1; // ok
    // derived1 = base; // error

    // 基类指针指向派生类对象，只能访问基类的部分
    Base * pbase = &derived1; // ok
    pbase->display();
    cout << pbase->_base << endl;
    // pbase->_derived; // error 只能访问基类的部分

    // 基类引用绑定派生类对象，通过这个引用只能访问基类的部分
    Base & rbase = derived1; // ok
    cout << rbase._base << endl;
    // rbase._derived; // error 只能访问基类的部分
    cout << derived1._derived << endl;
}
```



**结论：**

1. ==**可以用派生类对象赋值给基类对象**==
2. ==**可以用基类指针指向派生类对象**==
3. ==**可以用基类引用绑定派生类对象**==

**反之则均不可。**



## 派生类对象间的复制控制（重点）

**复制控制函数就是==拷贝构造函数==、==赋值运算符函数==**

原则：基类部分与派生类部分要单独处理

**（1）当派生类中==没有显式定义==复制控制函数时，就会==自动==完成基类部分的复制控制操作；**

**（2）当派生类中==有显式定义==复制控制函数时，不会再自动完成基类部分的复制控制操作，==需要显式地调用==；**

![image-20231102172052112](./cppbase.assets/image-20231102172052112.png)

- 对于拷贝构造，如果显示定义的派生类的拷贝构造，在其中不去显式调用基类的拷贝构造，编译器会直接报错（因为无法初始化基类的部分）


- 对于赋值运算符，如果显示定义的派生类的赋值运算符函数，在其中不去显示的调用基类的赋值运算符函数，那么基类的部分没有完成赋值操作


代码：

```c++
#include <iostream>
using std::cout;
using std::endl;

class Base {
public:
    Base(long base)
    : _base(base)
    {
        // cout << "Base()" << endl;
    }

    void display() const{
        cout << "Base::_base:" << _base << endl;
    }

    ~Base(){
        // cout << "~Base()" << endl;
    }

    long _base = 10;
};

class Derived
: public Base 
{
public:
    Derived(long base,long derived)
    : Base(base)
    , _derived(derived)
    {
        // cout << "Derived(long)" << endl;
    }

    ~Derived(){
        // cout << "~Derived()" << endl;
    }

#if 1
    /* 
        对于拷贝构造，如果显示定义的派生类的拷贝构造，
        在其中不去显式调用基类的拷贝构造，编译器会直接报错（因为无法初始化积累的部分）
    */
    Derived(const Derived & rhs)
    : Base(rhs) // 显式调用Base的拷贝构造 不写直接报错
    , _derived(rhs._derived)
    // : _derived(rhs._derived) // 报错
    {
        // cout << "Derived(const Derived &)" << endl;
    }

    /*
        对于赋值运算符，如果显示定义的派生类的赋值运算符函数，
        在其中不去显示的调用基类的赋值运算符函数，那么基类的部分没有完成赋值操作
    */
    Derived & operator=(const Derived & rhs){
        Base::operator=(rhs); // 需要显示调用，注释掉之后_base不会改
        _derived = rhs._derived;
        // cout << "Derived & operator=(const Derived & rhs)" << endl;
        return *this;
    }
#endif

    void display() const{
        Base::display();
        cout << "_derived: " << _derived << endl;
    }

    long _derived;
};

void test0(){
    Derived d1(2,3);
    Derived d2(7,8);
    Derived d3 = d2;
    d3.display();
    
    cout << endl;
    d2.display();
    cout << endl;
    d2 = d1;
    d2.display();
}

int main(void){
    test0();
    return 0;
}
```

如下，Derived对象没有指针成员申请堆空间，不需要显式定义拷贝构造函数和赋值运算符函数。编译器会自动完成基类部分的复制工作。但是如果在Derived类中显式写出了复制控制的函数，就需要显式地调用基类的复制控制函数。

``` c++
class Base{
public:
    Base(long base)
    : _base(base)
    {}

protected:
    long _base = 10;
};

class Derived
: public Base
{
public:
    Derived(long base, long derived)
    : Base(base)
    , _derived(derived)
    {}

    Derived(const Derived & rhs)
    : Base(rhs) // 调用Base的拷贝构造
    , _derived(rhs._derived)
    {
        cout << "Derived(const Derived & rhs)" << endl;
    }

    Derived &operator=(const Derived & rhs) // 调用Base的赋值运算符函数
    {
        Base::operator=(rhs);
        _derived = rhs._derived;
        cout << "Derived& operator=(const Derived &)" << endl;
        return *this;
    }

private:
    long _derived = 12;
};
```

如果Derived类的数据成员申请了**堆空间**，那么**必须手动写出Derived类的复制控制函数**，此时就要考虑到**基类的复制控制函数的显式调用**。（如果只是Base类的数据成员申请了堆空间，那么Base类的复制控制函数必须显式定义，Derived类自身的数据成员如果没有申请堆空间，不用显式定义复制控制函数）

**练习：将Base类的数据成员换成 `char *` 类型，体验一下派生类的复制**。

```c++
#include <string.h>
#include <iostream>
using std::cout;
using std::endl;

class Base {
public:
    Base(const char * pbase)
    : _pbase(new char[strlen(pbase) + 1]())
    { 
        strcpy(_pbase,pbase);
    }

    void display() const{
        cout << "_base:" << _pbase << endl;
    }

    ~Base(){ 
        if(_pbase){
            delete [] _pbase;
            _pbase = nullptr;
        }
    }

    Base(const Base & rhs)
    : _pbase(new char[strlen(rhs._pbase) + 1]())
    {
        strcpy(_pbase,rhs._pbase);
    }

    Base & operator=(const Base & rhs){
        if(this != &rhs){
            delete [] _pbase;
            _pbase = new char[strlen(rhs._pbase) + 1]();
            strcpy(_pbase,rhs._pbase);
        }
        return *this;
    }

    char * _pbase;
};

class Derived
: public Base 
{
public:
    Derived(const char * pbase,const char * pderived)
    : Base(pbase) // 显式调用基类的构造函数
    , _pderived(new char[strlen(pderived) + 1]())
    { 
        strcpy(_pderived,pderived);
    }

    ~Derived(){ 
        if(_pderived){
            delete [] _pderived;
            _pderived = nullptr;
        }
    }

#if 1
    Derived(const Derived & rhs)
    : Base(rhs) // 显式调用Base的拷贝构造
    , _pderived(new char[strlen(rhs._pderived) + 1]())
    { 
        strcpy(_pderived,rhs._pderived);
        cout << "Derived(const Derived &)" << endl;
    }

    Derived & operator=(const Derived & rhs){
        if(this != &rhs){
            // 需要显式调用Base的赋值运算符函数
            Base::operator=(rhs);
            delete [] _pderived;
            _pderived = new char[strlen(rhs._pderived) + 1]();
            strcpy(_pderived,rhs._pderived);
            cout << "Derived& operator=(const Derived&)" << endl;
        }
        return *this;
    }
#endif

    void display() const{
        Base::display();
        cout << "_derived:" << _pderived << endl; 
    }

    char * _pderived;
};

void test0(){
    Derived d1("hello","world");
    Derived d2 = d1;
    d2.display();

    d2 = d1;

    Derived d3("beijing","shanghai");
    d3  = d1;
    d3.display();
}


int main(void){
    test0();
    return 0;
}
```



- **对于派生类的拷贝构造函数**

如果给 Derived 类中添加一个 char * 成员，依然不显式定义 Derived 的复制控制函数。

那么进行派生类对象的复制时，基类的部分会完成正确的复制，派生类的部分只能完成==浅拷贝==（最终对象销毁时导致 double free 问题）

``` c++
Derived d1("hello","world",10);
Derived d2 = d1;
```

<img src="./cppbase.assets/image-20231102205721348.png" alt="image-20231102205721348" style="zoom:80%;" />

如果接下来给 Derived 类显式定义了拷贝构造，但是没有在这个拷贝构造中显式调用基类的拷贝构造（没有写任何的基类子对象的创建语句），会直接报错。因为没有初始化d2的基类子对象，需要在 derived 的拷贝构造函数中显式调用 Base 的拷贝构造。

<img src="./cppbase.assets/image-20231103100603636.png" alt="image-20231103100603636" style="zoom:80%;" />



- **对于赋值运算符函数**

如果接下来给 Derived 显式定义赋值运算符函数，但是没有在其中显式调用基类的赋值运算符函数

```` c++
Derived d1("hello","world",10);
Derived d2 = d1;
Derived d3("beijing","shanghai",10);

d2 = d3;  // 派生类对象的部分完成了复制，但是基类部分没有完成复制
````

<img src="./cppbase.assets/image-20231103101127183.png" alt="image-20231103101127183" style="zoom:67%;" />

基类的部分不会自动完成复制，需要在Derived的赋值运算符函数中显式调用Base的赋值运算符函数，才能完成正确的复制。

<img src="./cppbase.assets/image-20231103101238353.png" alt="image-20231103101238353" style="zoom:67%;" />



**总结：**

**给Derived类手动定义复制控制函数，注意在其中显式调用相应的基类的复制控制函数**。

**（注意：派生类对象进行复制时一定会马上调用派生类的复制控制函数，在进行复制时会==首先复制基类的部分==，此时调用基类的复制控制函数）**

``` c++
Derived(const Derived & rhs)
: Base(rhs) // 显式调用基类的拷贝构造
, _pderived(new char[strlen(rhs._pderived) + 1]())
{
    strcpy(_pderived, rhs._pderived);
    cout << "Derived(const Derived &)" << endl;
}

Derived & operator=(const Derived & rhs){
    cout << "Derived & operator=(const Derived &)" << endl;
    if(this != &rhs){
    // 显式调用基类的赋值运算符函数
    Base::operator=(rhs); // 关键
    delete [] _pderived;
    _pderived = new char[strlen(rhs._pderived) + 1]();
    strcpy(_pderived,rhs._pderived);
    _derived = rhs._derived;
    }
    return *this;
}
```



# 第八章 多态

1. **什么叫多态？**

   多态（ polymorphism ）是面向对象设计语言的基本特征之一。仅仅是将数据和函数捆绑在一起，进行类的封装，使用一些简单的继承，还不能算是真正应用了面向对象的设计思想。多态是面向对象的精髓。多态可以简单地概括为“**==一个接口，多种方法==**”。比如说：警车鸣笛，普通人反应一般，但逃犯听见会大惊失色，拔腿就跑。

   通常是指对于同一个消息、同一种调用，在不同的场合，不同的情况下，执行不同的行为 。

2. **为什么需要多态性？**

   我们知道，封装可以隐藏实现细节，使得代码模块化；继承可以扩展已存在的代码模块（类）。它们的目的都是为了**代码重用**。而多态除了代码的复用性外，还可以==解决项目中**紧偶合**的问题，提高程序的**可扩展性**==。

   如果项目耦合度很高的情况下，维护代码时修改一个地方会牵连到很多地方，会无休止的增加开发成本。而降低耦合度，可以保证程序的扩展性。而多态对代码具有很好的可扩充性。增加新的子类不影响已存在类的多态性、继承性，以及其他特性的运行和操作。实际上新加子类更容易获得多态功能。例如，在实现了圆锥、半圆锥以及半球体的多态基础上，很容易增添球体类的多态性。



**C++支持两种多态性：编译时多态和运行时多态。**

- **编译时多态**：也称为**静态多态**，我们之前学习过的==<u>函数重载</u>、<u>运算符重载</u>==就是采用的静态多态，C++编译器**==根据传递给函数的参数和函数名决定具体要使用哪一个函数==**，又称为**==静态联编==**。
- **运行时多态**：在一些场合下，**==编译器无法在编译过程中完成联编，必须在程序运行时完成选择==**，因此编译器必须提供这么一套称为“动态联编”（dynamic binding）的机制，也叫**==动态联编==**。C++通过**==虚函数==**来实现动态联编。接下来，我们提到的多态，不做特殊说明，指的就是动态多态。



## ==虚函数*==

虚函数的定义：==在一个成员函数的前面加上 `virtual` 关键字，该函数就成为虚函数==。

看这样一个例子：

基类和派生类中定义了**同名的 `display()` 函数**。

``` c++
class Base{
public:
    Base(long x)
    : _base(x)
    {}

    virtual void display() const{
        cout << "Base::display()" << endl;
    }
private:
    long _base;
};

class Derived
: public Base
{
public:
    Derived(long base,long derived)
    : Base(base) // 创建基类子对象
    , _derived(derived)
    {}

    void display() const{
        cout << "Derived::display()" << endl;
    }
private:
    long _derived;
};

void print(Base * pbase){
    pbase->display();
    pbase->show();
}

void test0(){
    Base base(10);
    Derived dd(1,2);

    print(&base);
    cout << endl;
    // 用一个基类指针指向派生类对象
    // 能够操纵的只有基类部分
    print(&dd);
    
    cout << "sizeof(Base):" << sizeof(Base) << endl;
    cout << "sizeof(Derived):" << sizeof(Derived) << endl;
}
```

得到的结果：

![image-20231103110913168](./cppbase.assets/image-20231103110913168.png)

——给Base中的display函数加上virtual关键字修饰，得到的结果：

![image-20231103110947711](./cppbase.assets/image-20231103110947711.png)

从运行结果中我们发现，virtual关键字加入后，发生了一件“奇怪”的事情 —— **用基类指针指向派生类对象后，通过这个基类对象竟然可以调用派生类的成员函数**。

而且，基类和派生类对象所占空间的大小都改变了，说明其内存结构发生了变化。

内存结构如下所示：

<img src="./cppbase.assets/image-20231103111110261.png" alt="image-20231103111110261" style="zoom:80%;" />



###  虚函数的实现原理

#### 虚函数指针

当 Base 的 `display()` 函数加上了 virtual 关键字，变成了一个虚函数，Base 对象的存储布局就改变了。

在**存储的开始位置**会多加一个**==虚函数指针==**，该虚函数指针指向一张**==虚函数表==**（简称**虚表**），其中虚函数表中存放的是**==虚函数的入口地址==**。

1. Derived 继承了 Base 类，那么创建一个 Derived 对象，依然会创建出一个 Base 类的基类子对象。

<img src="./cppbase.assets/image-20231103111930908.png" alt="image-20231103111930908" style="zoom:67%;" />

2. 在 Derived 类中又定义了 `display()` 函数（Base类中的 `display()` 函数已加上 `virtual` ），发生了**==覆盖==**的机制（override），==覆盖的是**虚函数表中虚函数的入口地址**==。

<img src="./cppbase.assets/image-20231103111511738.png" alt="image-20231103111511738" style="zoom:67%;" />

`Base* p` 去指向 Derived 对象，**依然==只能访问到基类的部分==**：

用指针p去调用 `display()` 函数，发现是一个虚函数，那么会**通过 `vfptr` 找到虚表**，此时虚表中存放的是 **`Derived::display()` 的入口地址**，所以调用到Derived的 `display()` 函数。

```c++
#include <iostream>
using std::cout;
using std::endl;

class Base{
public:
    Base(long x)
    : _base(x)
    {}

    // void display() const{
    //     cout << "Base::display()" << endl;
    // }

    virtual void display() const{
        cout << "Base::display()" << endl;
    }

// private:
    long _base;
};

class Derived
: public Base
{
public:
    Derived(long base,long derived)
    : Base(base)
    , _derived(derived)
    {}

    void display() const{
        cout << "Derived::display()" << endl;
    }

    void show() const{
        cout << "Derived::show()" << endl;
    }
/* private: */
    long _derived;
};

void print(Base * pbase){
    pbase->display();
}

void test0(){
    cout << sizeof(Base) << endl;
    cout << sizeof(Derived) << endl;

    Base base(1);
    Derived d1(10,20);

    // 调用各自的display()
    Base * p1 = &base;
    p1->display();
    p1->_base; // 类外访问私有成员 将_base设为public才可访问
    
    Derived * p2 = &d1;
    p2->display();

    // ---------------------基类指针指向派生类---------------------
    Base * p3 = &d1;
    p3->display(); // Derived::display()
    // p3->show(); // error 基类指针指向派生类，依然只能访问到基类的部分
    // p3->_derived; // error
    p3->_base; // 类外访问私有成员 将_base设为public才可访问
    // ---------------------------------------------------------

    print(p3); // Derived::display()
}

int main(void){
    test0();
    return 0;
}
```



#### 虚函数的覆盖	可加上`override`关键字

如果一个基类的成员函数定义为虚函数，那么它在所有派生类中也保持为虚函数，<u>即使在**派生类中省略了virtual关键字**，也仍然是虚函数</u>。虚函数一般用于灵活拓展，所以需要派生类中对此虚函数进行覆盖。覆盖的格式有一定的要求：

- 与基类的虚函数有相同的**==参数个数==**；

- 与基类的虚函数有相同的**==参数类型==**；

- 与基类的虚函数有相同的**==返回类型==**。



我们在派生类中对虚函数进行覆盖时，很有可能写错函数的形式（函数名、返回类型、参数个数），等到要使用时才发现没有完成覆盖。这种错误很难发现，所以 C++ 提供了关键字 `override` 来解决这一问题。

==**关键字override的作用**==：

在虚函数的函数参数列表之后，函数体的大括号之前，加上 `override` 关键字，**告诉编译器此处定义的函数是要对基类的虚函数进行覆盖。可以==检查函数的形式（函数名、返回类型、参数个数）是否写错==**。

``` c++
class Base{
public:
    virtual void display() const{
        cout << "Base::display()" << endl;
    }
private:
    long _base;
};

class Derived
: public Base
{
public:
    // 想要在派生类中定义虚函数覆盖基类的虚函数，很容易打错函数名字，同时又不会报错，没有完成有效的覆盖
    /* void dispaly() const{   //不会报错     */
    /* void dispaly() const override   //编译器会报错   */
    void display() const override
    {
        cout << "Derived::display()" << endl;
    }
    
private:
    long _derived;
};
```



**覆盖的总结：**  

1. 覆盖是在虚函数之间的概念，需要**派生类对象中定义的虚函数**与**基类中定义的虚函数**的**==形式完全相同==**；


2. 当基类中定义了虚函数时，派生类去进行覆盖，==**即使在派生类的同名的成员函数前不加virtual，依然是虚函数**==；

3. 发生在基类派生类之间，基类与派生类中同时定义相同的虚函数，<u>覆盖的是==**虚函数表中的入口地址**==，并不是覆盖函数本身</u>。



#### ==**动态多态（虚函数机制）被激活的条件**（重点*）==

虚函数机制是如何被激活的呢，或者说动态多态是怎么表现出来的呢？其实激活条件还是比较严格的，需要满足以下全部要求：

1. 基类定义虚函数
2. **派生类中要==覆盖==虚函数 **（覆盖的是虚函数表中的**地址信息**）
3. 创建派生类对象
4. **基类的(==指针==指向/==引用==绑定)派生类对象**
5. <u>**通过==基类==指针（引用）调用（派生类实现的）虚函数**</u>



==最终的效果：**基类指针调用到了派生类实现的虚函数**==。



#### ==虚函数表*==

==**在虚函数机制中virtual关键字的含义**==

1. 虚函数是存在的；**（存在）**

2. 通过间接的方式去访问；**（间接）**

   如果没有虚函数，当通过pbase指针去调用一个普通的成员函数，那么就不会通过虚函数指针和虚表，直接到程序代码区中找到该函数；有了虚函数，去找这个虚函数的方式就成了间接的方式。

3. 通过基类的指针访问到派生类的函数，基类的指针共享了派生类的方法。**（共享）**



> 对虚函数和虚函数表有了基本认知后，我们可以思考这样几个问题（**==面试常考题==**）
>
> 1. 虚表存放在哪里？
>
> 编译完成时，虚表应该已经存在；在使用的过程中，虚函数表不应该被修改掉（如果能修改，将会找不到对应的虚函数）——应该存在**==只读段（程序代码区，文字常量区）==**——具体位置不同厂家有不同实现。
>
> 
>
> 2. 一个类中虚函数表有几张？
>
> 虚函数表（虚表）可以理解为是一个数组，存放的是一个个虚函数的地址
>
> - 一个类可以**==没有==虚函数表**（没有虚函数就没有虚函数表）；
>
> - 可以有**==一张==虚函数表**（即使这个类有多个虚函数，将这些虚函数的地址都存在虚函数表中）；
>
> - 也可以有**==多张==虚函数表**（继承多个有虚函数的基类）一个基类一张虚函数表。
>
>
> <img src="./cppbase.assets/image-20231103114616212.png" alt="image-20231103114616212" style="zoom:67%;" />
>
> 
>
> <img src="./cppbase.assets/image-20231103114859866.png" alt="image-20231103114859866" style="zoom:67%;" />
>
> 
>
> 3. 虚函数的底层实现是怎样的？
>
> 虚函数的底层是通过**==虚函数表==**实现的。当类中定义了虚函数之后，就会**==在对象的存储开始位置，多一个虚函数指针，该虚函数指针指向一张虚函数表，虚函数表中存储的是虚函数入口地址==**。
>
> 
>
> 4. 三个概念的区分
>
> - 重载（overload）：发生在同一个类中， 当**函数名称相同**时 ，函数参数列表不同（函数参数类型、顺序 、个数任一不同）；
>
> - 隐藏（oversee）：发生在基类派生类之间 ，**函数名称相同**时，就构成隐藏（参数不同也能构成隐藏）；
>
> - 覆盖（override）：发生在基类派生类之间，基类与派生类中同时定义相同的虚函数，覆盖的是虚函数表中的入口地址，并不是覆盖函数本身。



###  虚函数的限制

虚函数机制给C++提供了灵活的用法，但仍然受到了一些约束，以下几种函数不能设为虚函数：

1. **==构造函数==**不能设为虚函数

   构造函数的作用是创建对象，完成数据的初始化，而==虚函数机制被激活的条件之一就是要**先创建对象，有了对象才能表现出动态多态**==。如果将构造函数设为虚函数，那此时构造未执行完，对象还没创建出来，存在矛盾。

   或者说，虚函数的调用需要虚函数表指针，而该指针存放在对象的内存空间中，若构造函数声明为虚函数，那么由于对象还未创建，还没有内存空间，更没有虚函数表vtable来调用虚构造函数。

   注意：**==<u>析构函数可以设为虚函数</u>==**，当基类的析构函数被声明为虚函数时，其派生类的析构函数也会自动成为虚函数。这样，当**==<u>*通过基类指针删除派生类对象*</u>时，系统会根据对象的实际类型来调用相应的析构函数，而不是仅仅调用基类的析构函数==**。

2. **==静态成员函数==**不能设为虚函数

   虚函数的实际调用：`this -> vfptr -> vtable -> virtual function`，但是静态成员函数没有this指针，所以无法访问到vfptr。

3. **==Inline函数==**不能设为虚函数

   因为inline函数在编译期间完成替换，而在编译期间无法展现动态多态机制，所以效果是冲突的如果同时存在，inline失效。

4. **==普通函数==**不能设为虚函数

   虚函数要解决的是对象多态的问题，与普通函数无关。



### 虚函数的各种访问情况

虚函数机制的触发条件中规定了要**使用==基类指针（或引用）==来调用虚函数**，那么其他的调用方式会是什么情况呢？

1. **通过==派生类对象==直接调用虚函数**

   并**没有满足<u>动态多态触发机制</u>的条件**，此时只是Derived中定义 `display()` 函数对 Base 中的 `display()` 函数发生了**隐藏**。

2. **在==构造函数==和==析构函数==中访问虚函数**

``` c++
class Grandpa
{
public:
    Grandpa(){ cout << "Grandpa()" << endl; }
    ~Grandpa(){ cout << "~Grandpa()" << endl; }

    virtual void func1() {
        cout << "Grandpa::func1()" << endl;
    }

    virtual void func2(){
        cout << "Grandpa::func2()" << endl;
    }
};

class Parent
: public Grandpa
{
public:
    Parent(){
        cout << "Parent()" << endl;
        //func1();//构造函数中调用虚函数
    }

    ~Parent(){
        cout << "Parent()" << endl;
        //func2();//析构函数中调用虚函数
    }
};

class Son
: public Parent
{
public:
    Son() { cout << "Son()" << endl; }
    ~Son() { cout << "~Son()" << endl; }

    virtual void func1() override {
        cout << "Son::func1()" << endl;
    }

    virtual void func2() override{
        cout << "Son::func2()" << endl;
    }
};

void test0(){
    Son ss;
    Grandpa * p = &ss;
    p->func1();
    p->func2();
}
```

用Grandpa类指针p指向Son类对象，用这个指针p调用 `func1()` 函数和 `func2()` 函数，结果是指针p调用到的是Son类的 `func1()` 和 `func2()` 函数。

说明**==即使Parent中没有对`func1()`和`fucn2()`覆盖（没有加上virtual关键字），在Son中也可以对`func1()`和`func2()`覆盖==**。

<img src="./cppbase.assets/image-20231103150156687.png" alt="image-20231103150156687" style="zoom:67%;" />



—— **如果在Parent类的==构造==和==析构==函数中调用虚函数**

创建一个son对象

![image-20240319223529548](./cppbase.assets/image-20240319223529548.png)

```c++
#include <iostream>
using std::cout;
using std::endl;

class Grandpa
{
public:
    Grandpa(){ 
        cout << "Grandpa()" << endl;
    }

    ~Grandpa(){
        cout << "~Grandpa()" << endl; 
    }

    virtual void func1() {
        cout << "Grandpa::func1()" << endl;
    }

    virtual void func2(){
        cout << "Grandpa::func2()" << endl;
    }
};

class Parent
: public Grandpa
{
public:
    Parent(){
        cout << "Parent()" << endl;
        func1();//构造函数中调用虚函数
    }

    ~Parent(){
        cout << "~parent()" << endl;
        func2();//析构函数中调用虚函数
    }
    
    // 执行析构函数时，写了就用Parent的，不写就用Grandpa的
    // virtual void func1(){
    //     cout << "Parent::func1()" << endl;
    // }

    // virtual void func2(){
    //     cout << "Parent::func2()" << endl;
    // }
};

class Son
: public Parent
{
public:
    Son() { cout << "Son()" << endl; }
    ~Son() { cout << "~Son()" << endl; }

    virtual void func1() override {
        cout << "Son::func1()" << endl;
    }

    virtual void func2() override{
        cout << "Son::func2()" << endl;
    }
};

void test0(){
    Son ss;
    Grandpa * p = &ss;
    p->func1(); // Son::func1()
    p->func2(); // Son::func2()
}

int main(void){
    test0();
    return 0;
}
```

在parent的构造函数执行时，并不知道是在构造Son的对象，在此过程中，**只能看到==本层及以上==的部分**（因为Grandpa类的基类子对象已经创建完毕，虚表中记录了`Grandpa::func1`和`Grandpa::func2`的地址）

在Parent的析构函数执行时，此时Son的析构函数已经执行完了，**也只能看到==本层及以上==的部分**。（表现的是静态联编）

——如果Parent类中也覆盖了`func1()`和`func2()`，那么会调用Parent本层的虚函数。



3. **在普通成员函数中调用虚函数**

``` c++
#include <iostream>
using std::cout;
using std::endl;
class Base{
public:
    Base(long x)
    : _base(x)
    {}

    virtual void display() const{
        cout << "Base::display()" << endl;
    }

    void func1(){
        display(); // 不加Base::作用域，derived.func1();调用到的就是Derived::display()
        cout << _base << endl;
    }

    void func2(){
        Base::display(); // 加上Base::作用域，derived.func2();调用到的就是Base::display()
    }

private:
    long _base = 10;
};

class Derived
: public Base
{
public:
    Derived(long base,long derived)
    : Base(base)
    , _derived(derived)
    {}

    void display() const override{
        cout << "Derived::display()" << endl;
    }

private:
    long _derived;
};

void test0(){
    Base base(10);
    Derived derived(1,2);
    base.func1(); // Base::display() 10
    base.func2(); // Base::display()
    derived.func1(); // Derived::display() 1
    derived.func2(); // Base::display()
}

int main(void){
    test0();
    return 0;
}
```

第1/2/4次调用，显然调用Base的display函数。



**==第3次调用的情况比较特殊：==**

derived对象调用func1函数，因为**Derived类中没有重新定义自己的func1函数，所以回去调用基类子对象的func1函数**。

可以理解为**this指针此时发生了向上转型，成为了Base*类型**。此时this指针还是指向的derived对象，就符合基类指针指向派生类对象的条件，在func1中调用虚函数display，触发动态多态机制。



### 抽象类

抽象类有两种形式：

1 . 定义了**纯虚函数**的类，称为抽象类

2 . 定义了**protected型构造函数**的类，也称为抽象类



####  纯虚函数

**纯虚函数**是一种特殊的虚函数，在许多情况下，==在基类中不能对虚函数给出有意义的实现，而把它声明为纯虚函数，**它的实现留给该基类的派生类去做**==。这就是纯虚函数的作用。纯虚函数的格式如下：

``` c++
class 类名 {
public:
	virtual 返回类型 函数名(参数 ...) = 0;
};
```

在基类中声明纯虚函数就是在告诉子类的设计者 —— 你必须提供一个纯虚函数的实现，但我不知道你会怎样实现它。

多个派生类可以对纯虚函数进行多种不同的实现，但是都需要遵循基类给出的接口（纯虚函数的声明）。

==注意：**定义了纯虚函数的类成为抽象类，抽象类不能实例化对象**==。

看一个简单例子：

``` c++
#include <iostream>
using std::cout;
using std::endl;

class A
{
public:
    virtual void print() = 0;
    virtual void display() = 0;
};

class B
: public A
{
public:
    virtual void print() override{
        cout << "B::print()" << endl;
    }

};

class C
: public B
{
public:
    virtual void display() override{
        cout << "C::display()" << endl;
    }
};

void test0(){
    //抽象类不能直接创建对象
    // A a;
    // B b;
    // A * pa = &b;

    C c;
    A * pa = &c;
    pa->print(); // B::print()
    pa->display(); // C::display()

    B * pb = &c;
    pb->print(); // B::print()
    pb->display(); // C::display()

    c.print(); // B::print()
    c.display(); // C::display()
}

int main(void){
    test0();
    return 0;
}
```

在A类中声明纯虚函数，A类就是抽象类，无法创建对象；

在B类中去覆盖A类的纯虚函数，如果把所有的纯虚函数都覆盖了（都实现了），B类可以创建对象；只要还有一个纯虚函数没有实现，B类也会是抽象类，也无法创建对象；

再往下派生C类，完成所有的纯虚函数的实现，C类才能够创建对象。

**最顶层的基类（定义纯虚函数的类）虽然无法创建对象，但是可以定义此类型的指针，指向派生类对象，去调用实现好的纯虚函数。**



**纯虚函数使用案例：**

实现一个图形库，获取图形名称，获取图形之后获取它的面积

``` c++
#include <math.h>
#include <iostream>
using std::cout;
using std::endl;
using std::string;

#define PI 3.14

class Figure{
public:
    virtual string getName() const = 0;
    virtual double getArea() const = 0;
};

class Rectangle // 矩形
: public Figure
{
public:
    Rectangle(double len,double wid)
    : _length(len)
    , _width(wid)
    {}

    string getName() const override
    {
        return "矩形";
    }
    double getArea() const override
    {
        return _length * _width;
    }
private:
    double _length;
    double _width;
};

class Circle
: public Figure
{
public:
    Circle(double r)
    : _radius(r)
    {}

    string getName() const override
    {
        return "圆形";
    }
    double getArea() const override
    {
        return PI * _radius * _radius;
    }
private:
    double _radius;
};

class Triangle
: public Figure
{
public:
    Triangle(double a,double b,double c)
    : _a(a)
    , _b(b)
    , _c(c)
    {}
  
    string getName() const override
    {
        return "三角形";
    }
    double getArea() const override
    {
        double p = (_a + _b + _c)/2;
        return sqrt(p * (p -_a) * (p - _b)* (p - _c));
    }
private:
    double _a,_b,_c;
};


void display(Figure * pf){
    cout << pf->getName() << endl; 
    cout << pf->getArea() << endl; 
}

void show(Figure & rhs){
    cout << rhs.getName() << endl;
    cout << rhs.getArea() << endl;
}

void test0(){
    Rectangle rectangle(10,20);
    Circle circle(10);
    Triangle triangle(3,4,5);

    display(&rectangle);
    display(&circle);
    display(&triangle);


    cout << endl;
    show(rectangle);
    show(circle);
    show(triangle);
    
    /* Figure ff; */
}

int main(void){
    test0();
    return 0;
}
```

基类Figure中定义纯虚函数，交给多个派生类去实现，最后可以使用基类的指针（引用）指向（绑定）不同类型的派生类对象，再去调用已经被实现的虚函数。

纯虚函数就是为了后续扩展而预留的接口。



#### 定义了protected构造函数的类

如果一个类**只定义了protected型的构造函数而没有提供public构造函数**，无论是在外部还是在派生类中作为其对象成员都不能创建该类的对象，但可以由其派生出新的类，这种能派生新类，却不能创建自己对象的类是另一种形式的抽象类。

Base类定义了protected属性，Base类也是抽象类，无法创建对象，<span style=color:red;background:yellow>**但是可以定义指针指向派生类对象**</span>

``` c++
#include <iostream>
using std::cout;
using std::endl;

class Base{
public:
    virtual void display() const{
        cout << "Base::display()" << endl;
    }

protected:
    Base(long x) // 只定义了protected型的构造函数而没有提供public构造函数
    : _base(x)
    {}

private:
    long _base;
};

class Derived
: public Base
{
public:
    Derived(long base,long derived)
    : Base(base) // 调用基类构造函数
    , _derived(derived)
    {}

    void display() const{
        cout << "Derived::display()" << endl;
    }

private:
    long _derived;
};

void test0(){
    // Base base(1); // error 无法创建对象
    Derived d1(2,3);
    Base * pbase = &d1; // 定义基类的指针指向派生类对象
    pbase->display();
}

int main(void){
    test0();
    return 0;
}
```



###  析构函数设为虚函数（==重点==）

虽然构造函数不能被定义成虚函数，但析构函数可以定义为虚函数，一般来说**，==如果类中定义了虚函数，析构函数也应被定义为虚析构函数，尤其是类内有<u>申请的动态内存，需要清理和释放</u>的时候。==**

``` c++
#include <iostream>
using std::cout;
using std::endl;

class Base{
public:
    Base()
    : _pbase(new int(10))
    {
        cout << "Base()" << endl;
    }

    virtual void display() const{
        cout << "*_pbase:" << *_pbase << endl;
    }
    
    virtual ~Base(){
        if(_pbase){
            delete _pbase;
            _pbase = nullptr;
        }
        cout << "~Base()" << endl;
    }

private:
    int * _pbase;
};

class Derived
: public Base
{
public:
    Derived()
    : Base()
    , _pderived(new int(20))
    {   cout << "Derived()" << endl;}

    virtual void display() const{
        cout << "*_pderived:" << *_pderived << endl;
    }

    ~Derived(){ // 执行结束后会自动调用派生类的析构函数，不需要显示调用，Grandpa例子
        if(_pderived){
            delete _pderived;
            _pderived = nullptr;
        }
        cout << "~Derived()" << endl;
    }

private:
    int * _pderived;
};

void test0(){
    Base * p = new Derived();
    p->display();
    delete p;
}

int main(void){
    test0();
    return 0;
}
```

在执行delete pbase时的步骤：

首先会去调用Derived的析构函数，但是此时是通过一个Base类指针去调用，无法访问到，只能跳过，20这个数据没有被删除

再去调用Base的析构函数，回收掉存放10这个数据的这片空间，

最后调用operator delete回收掉堆对象本身所占的整片空间（编译器知道需要回收的是堆上的Derived对象，会自动计算应该回收多大的空间，与delete语句中指针的类别没有关系 ——  delete pbase）



<img src="./cppbase.assets/image-20231103172246221.png" alt="image-20231103172246221" style="zoom:67%;" />



**为了<u>==让基类指针能够调用派生类的析构函数==</u>，需要==将Base的析构函数也设为虚函数==**。

Derived类中发生虚函数的覆盖，将Derived的虚函数表中记录的虚函数地址改变了。**析构函数尽管不重名，也认为发生了覆盖**。



<img src="./cppbase.assets/image-20231103173144167.png" alt="image-20231103173144167" style="zoom:80%;" />



总结：

<span style=color:red;background:yellow>**在实际的使用中，如果有通过基类指针回收派生类对象的需求，都要将基类指针的析构函数设为虚函数。**</span>



###  验证虚表的存在（==重点==）

从前面的知识讲解，我们已经知道虚表的存在，但之前都是理论的说法，我们是否可以通过程序来验证呢？——当然可以

``` c++
class Base{ // sizeof = 16 = 一个虚函数指针 + 一个long
public:
    virtual void print() {
        cout << "Base::print()" << endl;
    }
    virtual void display() {
        cout << "Base::display()" << endl;
    }
    virtual void show() {
        cout << "Base::show()" << endl;
    }
private:
 	long _base = 10;
};

class Derived // sizeof = 24 = 基类sizeof=16 + 派生类的long类型
: public Base
{
public:
    virtual void print() {
        cout << "Derived::print()" << endl;
    }
    virtual void display() {
        cout << "Derived::display()" << endl;
    }
    virtual void show() {
        cout << "Derived::show()" << endl;
    }
private:
    long _derived = 100;
};

void test0(){
	Derived d;
    long * pDerived = reinterpret_cast<long*>(&d); // 让指针强行看作long型数组，可以取下标
    cout << pDerived[0] << endl; // _vfptr指向虚函数表入口地址
    cout << pDerived[1] << endl; // 得到的是_base
    cout << pDerived[2] << endl; // 得到的是_derived

    cout << endl;
    long * pVtable = reinterpret_cast<long*>(pDerived[0]);
    cout << pVtable[0] << endl; // & Derived::print()
    cout << pVtable[1] << endl; // & Derived::display()
    cout << pVtable[2] << endl; // & Derived::show()

    cout << endl;
    typedef void (*Function)();
    Function f = (Function)(pVtable[0]); // pVtable[0]本质就是虚函数指针，弄个函数指针
    f(); // Derived::print()
    f = (Function)(pVtable[1]);
    f(); // Derived::display()
    f = (Function)(pVtable[2]);
    f(); // Derived::show()
    // 虚函数表中的顺序按照Base的顺序，Derived只是覆盖
}
```

创建一个Derived类对象d，这个对象的内存结构是由三个内容构成的，开始位置是虚函数指针，第二个位置是long型数据_base,

第三个位置是long型数据_derived。

第一次强转将这个Derived类对象视为了存放三个long型元素的数组，打印这个数组中的三个元素，后两个本身就是long型数据，输出其值，第一个本身是指针（地址），打印出来的结果是编译器以long型数据来看待这个地址的值。

这个虚函数指针指向虚表，虚表中存放三个虚函数的入口地址（3 * 8字节），那么再将虚表视为存放三个long型元素的数组，第二次强转，直接输出数组的三个元素，得到的结果是编译器以long型数据来看待这三个函数地址的值。

虚表中的三个元素本身是函数指针，那么再将这个三个元素强转成相应类型的函数指针，就可以通过函数指针进行调用了。

——验证了虚表中存放虚函数的顺序，是按照声明顺序去存放的。

<img src="./cppbase.assets/undefined202403142007724.png" alt="image-20240314200702615" style="zoom:67%;" />



###  带虚函数的多继承

描述：先是Base1、Base2、Base3都拥有虚函数f、g、h，Derived公有继承以上三个类，在Derived中覆盖了虚函数f，还有一个普通的成员函数g1，四个类各有一个double成员。

``` c++
#include <iostream>
using namespace std;

class Base1
{
public:
	Base1()
	: _iBase1(10)
	{
		cout << "Base1()" << endl;
	}

	virtual void f()
	{
		cout << "Base1::f()" << endl;
	}

	virtual void g()
	{
		cout << "Base1::g()" << endl;
	}

	virtual void h()
	{
		cout << "Base1::h()" << endl;
	}

	virtual ~Base1() {}

private:
	double _iBase1;
};

class Base2
{
public:
	Base2()
	: _iBase2(100)
	{
		cout << "Base2()" << endl;
	}

	virtual void f()
	{
		cout << "Base2::f()" << endl;
	}

	virtual void g()
	{
		cout << "Base2::g()" << endl;
	}

	virtual void h()
	{
		cout << "Base2::h()" << endl;
	}
private:
	double _iBase2;
};

class Base3
{
public:
	Base3()
	: _iBase3(1000)
	{
		cout << "Base3()" << endl;
	}

	virtual void f()
	{
		cout << "Base3::f()" << endl;
	}

	virtual void g()
	{
		cout << "Base3::g()" << endl;
	}

	virtual void h()
	{
		cout << "Base3::h()" << endl;
	}
private:
	double _iBase3;
};

class Derived
: public Base1
, public Base2
, public Base3
{
public:
	Derived()
	: _iDerived(10000)
	{
		cout << "Derived()" << endl;
	}

	void f()
	{
		cout << "Derived::f()" << endl;
	}

	void g1()
	{
		cout << "Derived::g1()" << endl;
	}

private:
	double _iDerived;
};

int main(void)
{
	cout << sizeof(Derived) << endl;

	Derived d;
	Base1* pBase1 = &d;
	Base2* pBase2 = &d;
	Base3* pBase3 = &d;

	cout << "&Derived = " << &d << endl;
	cout << "pBase1 = " << pBase1 << endl;
	cout << "pBase2 = " << pBase2 << endl;
	cout << "pBase3 = " << pBase3 << endl;

	return 0;
}
```



<img src="./cppbase.assets/image-20231104100627580.png" alt="image-20231104100627580" style="zoom:80%;" />



三种不同的基类类型指针指向派生类对象时，**实际指向的位置是==基类子对象的位置==**。

<img src="./cppbase.assets/image-20231104102102078.png" alt="image-20231104102102078" style="zoom:80%;" />

![image-20240320200632611](./cppbase.assets/image-20240320200632611.png)

VS上验证布局和虚函数表存放的内容

![image-20231104102414441](./cppbase.assets/image-20231104102414441.png)



#### 布局规则

1. 每个基类都有自己的虚函数表

2. 派生类如果有自己的虚函数，会被加入到==第一个虚函数表==之中

<img src="./cppbase.assets/image-20231104102926431.png" alt="image-20231104102926431" style="zoom:80%;" />

3. 内存布局中，其基类的布局按照==基类被声明时的顺序==进行排列（有虚函数的基类会往上放——希望尽快访问到虚函数）

<img src="./cppbase.assets/image-20231104103142140.png" alt="image-20231104103142140" style="zoom: 80%;" />

4. 派生类会覆盖基类的虚函数，==只有第一个虚函数表中存放的是真实的被覆盖的函数的地址；其它的虚函数表中对应位置存放的并不是真实的对应的虚函数的地址，而是一条跳转指令==。



#### 带虚函数的多重继承的二义性

例子：

``` c++
class A{
public:
    virtual void a(){ cout << "A::a()" << endl; } 
    virtual void b(){ cout << "A::b()" << endl; } 
    void c(){ cout << "A::c()" << endl; } 
};

class B{
public:
    virtual void a(){ cout << "B::a()" << endl; } 
    virtual void b(){ cout << "B::b()" << endl; } 
    void c(){ cout << "B::c()" << endl; } 
    void d(){ cout << "B::d()" << endl; } 
};

class C
: public A
, public B
{
public:
    virtual void a(){ cout << "C::a()" << endl; } 
    void c(){ cout << "C::c()" << endl; } 
    void d(){ cout << "C::d()" << endl; } 
};

class D
: public C
{
public:
    void c(){ cout << "D::c()" << endl; }
};
```

内存结构的示意图：

<img src="./cppbase.assets/image-20231104112118817.png" alt="image-20231104112118817" style="zoom:80%;" />



<font color=red>**请分析以下各种调用情况的结果**</font>

``` c++
void test0(){
    cout << sizeof(A) << endl; // 8
    cout << sizeof(B) << endl; // 8
    cout << sizeof(C) << endl; // 16
    cout << sizeof(D) << endl; // 16

    /*
        隐藏(oversee)：发生在基类派生类之间，函数名称相同时，就构成隐藏（参数不同也能构成隐藏）；
        覆盖(override)：发生在基类派生类之间，基类与派生类中同时定义相同的虚函数，覆盖的是虚函数表中的入口地址，并不是覆盖函数本身
    */
    C c;
    c.a(); // C::a() 隐藏
    // c.b(); // 冲突 不知道该用A::b()还是B::b()
    c.c(); // C::c() 隐藏
    c.d(); // C::d() 隐藏

    cout << endl;
    A* pa = &c;
    pa->a(); // C::a() 覆盖
    pa->b(); // A::b()
    pa->c(); // C::c() 覆盖
    // pa->d(); // 无法调用 基类指针只能访问到基类的部分

    cout << endl;
    B* pb = &c;
    pb->a(); // C::a() 覆盖
    pb->b(); // B::b()
    pb->c(); // B::c() 不是虚函数调用
    pb->d(); // B::d() 同上

    cout << endl;
    C * pc = &c;
    pc->a(); // C::a() 隐藏
    // pc->b(); //冲突
    pc->c(); // C::c() 隐藏
    pc->d(); // C::d() 隐藏
}
```



——思考：pc->c()   这里的c函数是不是虚函数

从内存的角度分析，C::c()已经在第一张虚函数表中了，所以应该当成是虚函数处理。能否验证一下呢？

D类继承C类，重新定义c()函数，用C类指针指向D类对象，并调用c()函数

<img src="./cppbase.assets/undefined202403201126842.png" alt="image-20240320112649770" style="zoom:67%;" />

如果将A类中c函数的virtual关键字去掉，毫无疑问C中c函数是一个普通函数（发生的是隐藏）



##  虚拟继承

### 虚函数 vs 虚拟继承

**在虚函数机制（动态多态机制）中**

1、虚函数是存在的；**（存在）**

2、通过间接的方式去访问；**（间接）**

3、通过基类的指针访问到派生类的函数，基类的指针共享了派生类的方法**（共享）**

（如果没有虚函数，当通过pbase指针去调用一个普通的成员函数，那么就不会通过虚函数指针和虚表，直接到程序代码区中找到该函数；有了虚函数，去找这个虚函数的方式就成了间接的方式）



**虚拟继承同样使用virtual关键字（存在、间接、共享）**

1、存在即表示虚继承体系和虚基类确实存在

2、间接性表现在当访问虚基类的成员时同样也必须通过某种间接机制来完成（通过虚基表来完成）

3、共享性表现在虚基类会在虚继承体系中被共享，而不会出现多份拷贝



**==虚基类==：**

**如果B类虚拟继承了A类，那么说A类是B类虚基类，因为A类还可以以非虚拟的方式派生其他类**



补充：

（1）虚拟继承的内存结构

**==虚基表==**中记录从虚机指针到基类子对象的成员需要偏转的信息

![image-20240320113652896](./cppbase.assets/undefined202403201136017.png)



（2）如果虚基类中包含了虚函数

**==虚基表==**中记录从虚机指针到基类子对象的成员需要偏转的信息

**虚函数表**记录虚函数的内容

![image-20240320114034388](./cppbase.assets/undefined202403201140477.png)



（3）如果派生类中又定义了新的虚函数，会在内存中多出一个属于派生类的虚函数指针，指向一张新的虚表（VS的实现）

c类自己定义了一个`run3()`虚函数，

派生类定义了一个自己的虚拟函数，放到第一张虚表中，不会多一个虚表

但是现在多了一个虚表出来，虚函数指针存放了`run3()`派生类自己定义的虚函数

现在虚基表`C::$vbtable@:`中-8记录了到派生类对象首地址所需要的偏移量，8为到虚基类子对象首地址所需的偏移量

![image-20240320114653517](./cppbase.assets/undefined202403201146618.png)



（4）带虚函数的菱形继承（拔高，不要求一定掌握）

<img src="./cppbase.assets/undefined202403201208905.png" alt="image-20240320120838811" style="zoom:67%;" />



<img src="./cppbase.assets/undefined202403201211237.png" alt="image-20240320121100154" style="zoom:67%;" />



### 虚拟继承时派生类对象的构造和析构

如下菱形继承的结构中，中间层基类虚拟继承了顶层基类，注意底层派生类的构造函数

``` c++
class A
{
public:
    A(double a)
    : _a(a)
    {
        cout << "A(double)" << endl;
    }

    ~A(){cout << "~A()" << endl;}
private:
    double _a = 10;
};

class B
: virtual public A
{
public:
    B(double a, double b)
    : A(a)
    , _b(b)
    {
        cout << "B(double,double)" << endl;
    }

    ~B(){ cout << "~B()" << endl; }
private:
    double _b;
};


class C
: virtual public A
{
public:
    C(double a, double c)
    : A(a)
    , _c(c)
    {
        cout << "C(double,double)" << endl;
    }

    ~C(){ cout << "~C()" << endl; }
private:
    double _c;
};

class D
: public B
, public C
{
public:
    D(double a,double b,double c,double d)
    : A(a)
    , B(a,b)
    , C(a,c)
    , _d(d)
    {
        cout << "D(double * 4)" << endl;
    }

    ~D(){ cout << "~D()" << endl; }
private:
    double _d;
};
```

<img src="./cppbase.assets/undefined202403201441781.png" alt="image-20240320144100684" style="zoom:67%;" />

<font color=red>**在虚拟继承的结构中，最底层的派生类不仅需要==显式调用中间层基类的构造函数==，还要在==初始化列表最开始调用顶层基类的构造函数==。**</font>



——那么A类构造岂不是会调用3次？

并不会，有了A类的构造之后会压抑B、C构造时调用A类构造，A类构造只会调用一次。可以对照菱形继承的内存模型理解，D类对象中只有一份A类对象的内容。

<img src="./cppbase.assets/image-20240314222140311.png" alt="image-20240314222140311" style="zoom:67%;" />



### 效率分析

多重继承和虚拟继承对象模型较单一继承复杂的对象模型，造成了成员访问低效率，表现在两个方面：对象构造时 vptr 的多次设定，以及 this 指针的调整。对于多种继承情况的效率比较如下：

<img src="./cppbase.assets/image-20231104091618905.png" alt="image-20231104091618905" style="zoom:80%;" />



# 第九章 模板

现在的 C++ 编译器实现了一项新的特性：模板（ Template ）。

模板是一种通用的描述机制，使用模板允许使用通用类型来定义函数或类。在使用时，通用类型可被具体的类型，如 int、double 甚至是用户自定义的类型来代替。模板引入一种全新的编程思维方式，称为“**泛型编程**”或“**通用编程**”。

##  为什么要定义模板

像 C/C++/Java 等语言，是**编译型语言**，先编译后运行。它们都有一个强大的类型系统，也被称为**强类型语言**，希望在程序执行之前，尽可能地发现错误，防止错误被延迟到运行时。所以会对语言本身的使用造成一些限制，称之为**静态语言**。

与之对应的，还有**动态语言**，也就是**解释型语言**。如javascript/python/Go，在使用的过程中，一个变量可以表达多种类型，也称为**弱类型语言**。因为没有编译的过程，所以相对更难以调试。



强类型程序设计中，参与运算的**所有对象的类型在编译时即确定下来**，并且编译程序将进行严格的类型检查。为了解决强类型的严格性和灵活性的冲突，也就是在严格的语法要求下尽可能提高灵活性，有以下3中方式解决：

- 带参数宏定义（原样替换）

- 重载函数（函数名相同，函数参数不同）

- ==**模板**（将**数据类型**作为参数）==

```C++
int add(int x, int y){
    return x + y;
}

double add(double x, double y){
    return x + y;
}

long add(long x, long y){
    return x + y;
}

string add(string x, string y){
    return x + y;
}

// 希望将类型参数化
// 使用class关键字或typename关键字都可以
template <class/typename T>
T add(T x, T y)
{
    return x + y;
}
```

模板作为实现代码重用机制的一种工具，它可以实现类型参数化，即把类型定义为参数， 从而实现了真正的代码可重用性。

模板可以分为两类，一个是**==函数模版==**，另外一个是**==类模板==**。通过参数实例化构造出具体的函数或类，称为**模板函数**或**模板类**。

```c++
// <函数模板>
template <class T>
T add(T t1, T t2)
{
    return t1 + t2;
}

#if 0
// 编译器在走到模板被使用的语句时，会生成一个<模板函数>（实例化）
short add(short x,short y){
    return x + y;
}
#endif
```



## 模板的定义

**模板发生的时机是在==编译时==**

模板本质上就是一个**代码生成器**，它的作用就是让编译器根据实际调用来生成代码。

编译器去处理时，实际上由函数模板生成了多个模板函数，或者由类模板生成了多个模板类。

<img src="./cppbase.assets/image-20231106103729892.png" alt="image-20231106103729892" style="zoom:80%;" />

```c++
// 函数模板的形式
template <模板参数列表>
函数的返回类型 函数名字(函数的参数列表)
{ ... }

template <typename T1, typename T2, ...>
template <class T1, class T2, ...>
// 模板参数列表中typename与class的含义是完全一样
```



### 函数模板

由函数模板到模板函数的过程称之为<span style=color:red;background:yellow>**实例化**</span>

**==函数模板 --> 生成相应的模板函数 -->编译 -->链接 -->可执行文件==**

下图中实际上可以理解为生成了四个模板函数

``` c++
template <class T>
T add(T t1,T t2)
{ return t1 + t2; }

void test0(){
    short s1 = 1, s2 = 2;
    int i1 = 3, i2 = 4;
    long l1 = 5, l2 = 6;
    double d1 = 1.1, d2 = 2.2;
    // 通过传入的参数类型确定出模板类型 隐式实例化
    cout << "add(s1,s2): " << add(s1,s2) << endl;
    cout << "add(i1,i2): " << add(i1,i2) << endl;
    cout << "add(l1,l2): " << add(l1,l2) << endl;
    cout << "add(d1,d2): " << add(d1,d2) << endl;  
}
```

上述代码中在进行模板实例化时，并没有指明任何类型，函数模板在生成模板函数时**==通过传入的参数类型确定出模板类型==**，这种做法称为<span style=color:red;background:yellow>**隐式实例化**</span>。

我们在使用函数模板时还可以**==在函数名之后直接写上模板的类型参数列表==**，指定类型，这种用法称为<span style=color:red;background:yellow>**显式实例化**</span>。

``` c++
template <class T>
T add(T t1,T t2)
{ return t1 + t2; }

void test0(){
    double i1 = 3.1, i2 = 4.2;
    // 在函数名之后直接写上模板的类型参数列表 显示实例化
    cout << "add(i1,i2): " << add<int>(i1,i2) << endl;
}
```



#### 函数模板的重载

##### 函数模板与函数模板重载的条件

**条件：名称相同（这是必须的）**

- 方法（1）**返回类型不同**(并非指推导出的具体返回类型不同，而是**返回类型在模板==参数列表中的位置==不同**）

  <span style=color:red;background:yellow>**但是强烈不建议进行这样的重载**</span>。

  但是这样进行重载时，要注意，隐式实例化可能造成冲突，需要显式实例化。（如果能够通过类型转换去匹配上两个函数模板的时候，即使是显式实例化也很难避免冲突）

  ```c++
  template <class T1, class T2>  // 模板二
  T2 add(T1 t1, T2 t2)
  {
  	cout << "T1,T2" << endl;
  	return t1 + t2;
  } 
  
  template <class T1, class T2>  // 模板二
  T2 add(T2 t2, T1 t1)
  {
  	cout << "T1,T2" << endl;
  	return t1 + t2;
  }
  ```

  

- 方法（2）**模板参数的个数不同**（推荐）

  ```c++
  #include <iostream>
  using std::cout;
  using std::endl;
  
  template <class T>
  T add(T t1, T t2)
  {
      cout << "T class" << endl;
      return t1 + t2;
  }
  
  template <class T1, class T2>
  T1 add(T1 t1, T2 t2)
  {
      cout << "T1,T2" << endl;
      return t1 + t2;
  }
  
  template <class T1, class T2, class T3>
  T1 add(T1 t1, T2 t2, T3 t3)
  {
      cout << "T1,T2,T3" << endl;
      return t1 + t2 + t3;
  }
  
  void test0(){
      double x = 1.2;
      double y = 2.9;
      int z = 11;
      cout << add(x,y) << endl; // 模板一 4.1
      cout << add(x,z) << endl; // 模板二 12.2
      cout << add(z,x) << endl; // 模板二 12
      cout << add(x,y,z) << endl; // 模板三 15.1
      cout << add(z,x,y) << endl; // 模板三 15
  }
  
  int main(void){
      test0();
      return 0;
  }
  ```



##### 函数模板可以与==函数模板==进行重载

> 如果在使用函数模板时传入两个不同类型的参数，会出错，此时就需要进行显式实例化。
>
> 如下，指定了类型T为int型，虽然s1是short型数据，但是会发生类型转换。这个转换没有问题，因为int肯定能存放short型数据的所有内容。
>
> ``` c++
> template <class T>
> T add(T t1,T t2)
> { return t1 + t2; }    
> 
> void test0(){
> 	short s1 = 1;
>  int i2 = 4;
>  cout << "add(s1,s2): " << add(s1,i2) << endl; 	   // error 隐式实例化
>  cout << "add(s1,s2): " << add<int>(s1,i2) << endl; // ok 显式实例化
> }
> ```
>
> 但如果是以下这种转换，实际上就会损失数据精度。此时的d2会转换成int型。
>
> ``` c++
> int i1 = 4；
> double d2 = 5.3;
> cout << "add(i1,d2): " << add<int>(i1,d2) << endl;
> ```
>
> 
>
> 如果一个函数模板无法实例化出合适的模板函数（去进行显式实例化也有一些问题）的时候，可以再给出另一个函数模板
>
> ``` c++
> // 函数模板与函数模板重载：模板参数个数不同,ok
> template <class T> // 模板一
> T add(T t1,T t2)
> { return t1 + t2; }
> 
> template <class T1, class T2> // 模板二
> T1 add(T1 t1, T2 t2)
> { return t1 + t2; }
> 
> double x = 9.1;
> int y = 10;
> cout << add(x,y) << endl; // 会调用模板二生成的模板函数，不会损失精度
> // 试一试
> cout << add(y,x) << endl; // 返回值是一个int数据
> ```
>
> 如果仍然采用显式实例化：
>
> - 可以传入两个类型参数，那么一定会调用模板二生成的模板函数。传入的两个类型参数会作为T1、T2的实例化参数。
>
> - 也可以传入一个类型参数，那么这个参数会作为模板参数列表中的第一个类型参数进行实例化。
>
> 如果仍然需要进行类型转换，那么就会使用第一个函数模板进行实例化；
>
> 如果不需要进行类型转换，就会使用第二个函数模板进行实例化。
>
>    ``` c++
> void test1(){
>     int x = 10;
>     double y = 9.2;
>     double z = 9.9;
> 
>     // ================有了<模板二>之后==================
>     // 显式实例化时可以指定两个类型参数
>     cout << add<int,int>(x,y) << endl;      // 模板二 19
>     cout << add<int,double>(x,y) << endl;   // 模板二 19
> 
>     // 指定了T1类型为int，没有指定T2类型
>     cout << add<int>(x,y) << endl;          // 模板二 19
>     // 指定了T1类型为double，没有指定T2类型
>     cout << add<double>(y,x) << endl;       // 模板二 19.2
> 
>     // 能够隐式实例化尽量使用隐式实例化
>     cout << add(x,y) << endl;               // 模板二 19
>     cout << add(y,x) << endl;               // 模板二 19.2
>     cout << add(y,z) << endl;               // 模板二 19.1
> 
>     cout << endl;
> 
>     // 注意：不推荐如下写法
>     // 指定了返回类型（T1）是int，没有指定T2类型
>     /* 
>     对照模板二，发现第一个参数(T1)也得是int，实际传入的y是double，
>     再对照模板一，模板一只有一个类型参数，就直接使用了（对y进行类型转换）
>     */
>     cout << add<int>(y,x) << endl;          // 模板一 19
> 
>     // 指定了返回值类型(T1)为double，没有指定T2类型
>     /* 
>     对照模板二，发现第一个参数(T1)也得是double，实际传入的y是int，
>     再对照模板一，模板一只有一个类型参数，就直接使用了（对y进行类型转换）
>     */
>     cout << add<double>(x,y) << endl;       // 模板一 19.2
> }
>    ```



##### 函数模板与==普通函数==重载

> ==普通函数优先于函数模板执行==——因为普通函数更快
>
> （编译器扫描到函数模板的实现时并没有生成函数，只有扫描到下面调用**add函数**的语句时，给add传参，知道了参数的类型，这才生成一个相应类型的模板函数——模板参数推导。所以使用函数模板一定会增加编译的时间。此处，就直接调用了普通函数，而不去采用函数模板）
>
> ``` c++
> // 函数模板与普通函数重载
> template <class T1, class T2>
> T1 add(T1 t1, T2 t2)
> {
>  return t1 + t2;
> }
> 
> short add(short s1, short s2){
>  cout << "add(short,short)" << endl;
>  return s1 + s2;
> }
> 
> void test1(){
>  short s1 = 1, s2 = 2;
>  // 可以调用函数模板
>  // T1和T2推导出的结果——参数类型可以相同
>  // 当普通函数和函数模板重载时
>  // 优先使用普通函数，因为效率更高（更直接）
>  cout << add(s1,s2) << endl;
> }
> ```



#### 头文件与实现文件形式（重要）

问题：为什么C++头文件没有所谓的.h后缀？

> **在一个源文件中，函数模板的声明与定义可以分离**
>
> 即使把函数模板的实现放在调用之下也是ok的，与普通函数一致。
>
> ```c++
> // 函数模板的声明
> template <class T>
> T add(T t1, T t2)
> 
> void test1(){ 
>     int i1 = 1, i2 = 2;
> 	cout << add(i1,i2) << endl;
> }
> 
> // 函数模板的实现
> template <class T>
> T add(T t1, T t2)
> {
>     return t1 + t2;
> }
> ```



> **如果在不同文件中进行==分离==**
>
> 如果像普通函数一样去写出了头文件、实现文件、测试文件，==编译`g++ testAdd.cc add.cc`报错==
>
> ``` c++
> // add.h 头文件
> template <class T>
> T add(T t1, T t2);
> 
> // add.cc 实现文件
> #include "add.h"
> template <class T>
> T add(T t1, T t2)
> {
>  return t1 + t2;
> }
> 
> // testAdd.cc 测试文件
> #include "add.h"
> void test0(){
>  int i1 = 1, i2 = 2;
>  cout << add(i1,i2) << endl;
> }
> ```
>
> 
>
> <img src="./cppbase.assets/image-20231106112336018.png" alt="image-20231106112336018" style="zoom:80%;" />



> - **单独编译实现文件**`add.cc`，使之生成目标文件，查看目标文件，会发现没有生成任何与add相关的内容。
>
> <img src="./cppbase.assets/image-20231106112510178.png" alt="image-20231106112510178" style="zoom:80%;" />



> - **单独编译测试文件**`testAdd.cc`，发现有与add名称相关的函数，但是没有地址，这就表示只有声明。
>
> <img src="./cppbase.assets/image-20231106112645716.png" alt="image-20231106112645716" style="zoom:80%;" />



> - 在”实现文件“中要进行==**调用**==，因为**有了调用才有==推导==，才能由函数模板==生成需要的函数==**
>
>
> ``` c++
> template <class T>
> T add(T t1, T t2)
> {
> 	return t1 + t2;
> }
> 
> // 在这个文件中如果只是写出了函数模板的实现
> // 并没有调用的话，就不会实例化出模板函数
> void test1(){ 
> 	cout << add(1,2) << endl;
> }
> ```
>
> 
>
> 此时单独编译实现文件，发现生成了对应的函数
>
> <img src="./cppbase.assets/image-20231106113306412.png" alt="image-20231106113306412" style="zoom:80%;" />



但是在“实现文件”中对函数模板进行了调用，这种做法不优雅。



设想：**如果在测试文件调用时，推导的过程中，<font color=red>看到的是完整的模板的代码</font>，那么应该可以解决问题**

``` c++
// add.h
template <class T>
T add(T t1, T t2);

#include "add.cc"
```

==在头文件中加上`#include "add.cc"`==，即使实现文件中没有调用函数模板，单独编译testAdd.cc，也可以发现问题已经解决。

因为本质上相当于==**把函数模板的定义写到了头文件中**==。



<span style=color:red;background:yellow>**总结：**</span>

**对模板的使用，必须要拿到模板的==全部实现==，如果只有一部分，那么推导也只能推导出一部分，无法满足需求。**

**换句话说，就是模板的使用过程中，其实没有了头文件和实现文件的区别，==在头文件中也需要获取模板的完整代码，不能只有一部分==。**



问题的原因：C++的标准库都是由模板开发的，所以经过标准委员的商讨，<font color=red>**将这些头文件取消了后缀名，与C的头文件形成了区分；这些实现文件的后缀名设为了tcc**</font>



#### 模板的特化（模板的具体化）

在函数模板的使用中，有时候会有一些通用模板处理不了的情况，我们可以定义普通函数或特化模板来解决。虽然普通函数的优先级更高，但有些场景下是必须使用特化模板的。



- **全特化**：将模板的参数列表中的参数全部以特殊版本的形式写出来

  它的形式是固定的：

  1. template后直接跟 <> ，里面不写类型
  2. 在函数名后跟 <> ，其中写要特化的类型

  ```` c++
  // 特化模板（全特化）
  // 这里就是告诉编译器这里是一个模板
  template <>
  const char * add<const char *>(const char * p1,const char * p2){
      // 先开空间
      char * ptmp = new char[strlen(p1) + strlen(p2) + 1]();
      strcpy(ptmp,p1);
      strcat(ptmp,p2);
      return ptmp;
  }
  ````


- **偏特化**（部分特化）：将模板参数列表中的参数类型，至少有一个(T3)没有特化出来。

<img src="./cppbase.assets/image-20220601102808407-1711525523058-1.png" alt="image-20220601102808407" style="zoom:80%;" />



**注意：**

<font color=red>**使用模板特化时，必须要先有==基础的函数模板==**</font>

如果没有模板的通用形式，无法定义模板的特化类型

特化模板是为了解决通用模板无法处理的特殊类型的操作

```c++
template <class T1, class T2>
T1 add(T1 t1, T2 t2)
{
    return t1 + t2;
}

// const char * add(const char * p1,const char * p2){
//     char * ptemp = new char[strlen(p1) + strlen(p2) + 1]();
//     strcpy(ptemp,p1);
//     strcat(ptemp,p2);
//     return ptemp;
// }

// 特化模板
template <>
const char * add<const char *>(const char * p1,const char * p2){
    char * ptemp = new char[strlen(p1) + strlen(p2) + 1]();
    strcpy(ptemp,p1);
    strcat(ptemp,p2);
    return ptemp;
}
```

特化版本的**函数名**、**参数列表**要和原基础的模板函数相同，避免不必要的错误。



#### 使用模板的规则（重要）

1. **在一个模块中定义多个通用模板的写法应该谨慎使用；**
2. **调用函数模板时尽量使用==隐式调用==，让编译器推导出类型；**
3. **无法使用隐式调用的场景，只指定必须要指定的类型；**
4. **需要使用特化模板的场景，根据特化模板，将类型要指定清楚。**



#### 模板的参数类型（可以赋默认值）

1. 类型参数

   之前的T/T1/T2等等成为**模板参数**，也称为**类型参数**，类型参数T可以写成任何类型

2. 非类型参数

   **需要是==整型数据==， char/short/int/long/size_t等**

   不能是浮点型，float/double不可以



定义模板时，在模板参数列表中除了类型参数还可以加入非类型参数。如下，调用模板时需要传入非类型参数的值

``` c++
template <class T,int kBase>
T multiply(T x, T y){
    return x * y * kBase;
}

void test0(){
    int i1 = 3,i2 = 4;
    cout << multiply<int,10>(i1,i2) << endl;
}
```



可以**==给非类型参数赋默认值==**，有了默认值后调用模板时就可以不用传入这个非类型参数的值

``` c++
template <class T,int kBase = 10>
T multiply(T x, T y){
    return x * y * kBase;
}

void test0(){
    int i1 = 3,i2 = 4;
    cout << multiply<int,10>(i1,i2) << endl;
    cout << multiply<int>(i1,i2) << endl;
    cout << multiply(i1,i2) << endl;
}
```

<img src="./cppbase.assets/undefined202403211515725.png" alt="image-20240321151554666" style="zoom:67%;" />



函数模板的模板参数赋默认值与普通函数相似，从右到左

``` c++
template <class T = int,int kBase = 10>
T multiply(T x, T y){
    return x * y * kBase;
}

void test0(){
    double d1 = 1.2, d2 = 1.2;
    // 模板的非类型参数赋默认值后
    // 调用模板时可以隐式实例化
    cout << multiply(d1,d2) << endl;
    cout << multiply<int>(d1,d2) << endl;
}
```

<img src="./cppbase.assets/undefined202403211518540.png" alt="image-20240321151834486" style="zoom:67%;" />

==**优先级：指定的类型  >  推导出的类型  > 类型的默认参数**==



模板参数的默认值（不管是类型参数还是非类型参数）只有在没有足够的信息用于推导时起作用。当存在足够的信息时，编译器会按照实际参数的类型去调用，不会受到默认值的影响。



#### 成员函数模板

在一个普通类中也可以定义成员函数模板

```c++
class Point
{
public:
    Point(double x,double y)
    : _x(x)
    , _y(y)
    {}

    // 定义一个成员函数模板
    // 将_x转换成目标类型
    template <class T>
    T convert()
    {
        return (T)_x;
    }

    private:
    double _x;
    double _y;
};

void test0(){
    Point pt(1.1,2.2);
    cout << pt.convert<int>() << endl;
    cout << pt.convert() << endl; // error
}
```

——此时调用这个成员函数模板，不能采用隐式实例化的方式，不知道要将pt._x转换成什么类型

```c++
// 定义一个成员函数模板
// 将_x转换成目标类型
template <class T = int>
T convert()
{
	return (T)_x;
}

cout << pt.convert() << endl; // ok
```



——可以给成员函数模板中类型参数赋默认值，有了默认值后才可以进行隐式实例化

<img src="./cppbase.assets/undefined202403211532873.png" alt="image-20240321153232814" style="zoom:67%;" />



—— 如果要将成员函数模板在类之外进行实现

<img src="./cppbase.assets/undefined202403211535662.png" alt="image-20240321153505617" style="zoom:67%;" />





###  类模板

一个类模板允许用户为类定义个一种模式，使得类中的某些数据成员、默认成员函数的参数，某些成员函数的返回值，能够取任意类型(包括内置类型和自定义类型)。

如果一个类中的数据成员的数据类型不能确定，或者是某个成员函数的参数或返回值的类型不能确定，就需要将此类声明为模板，它的存在不是代表一个具体的、实际的类，而是代表一类的类。



**类模板**的定义形式如下：

``` c++
template <class/typename T, ...>
class 类名{
	// 类定义．．．．．．
};
```



示例，用类模板的方式实现一个Stack类，可以存放任意类型的数据

——使用函数模板实例化模板函数使用类模板实例化模板类

<img src="./cppbase.assets/image-20231106163746558.png" alt="image-20231106163746558"  />

``` c++
template <class T, int kCapacity = 10>
class Stack
{
public:
    Stack()
    : _top(-1)
    , _data(new T[kCapacity]())
    {
        cout << "Stack()" << endl;
    }
    ~Stack(){
        if(_data){
            delete [] _data;
            _data = nullptr;
        }
        cout << "~Stack()" << endl;
    }
    bool empty() const;
    bool full() const;
    void push(const T &);
    void pop();
    T top();
private:
    int _top;
    T * _data;
};
```



类模板的成员函数如果放在类模板定义之外进行实现，需要注意

（1）**需要带上template模板形参列表**（如果有默认参数，此处不要写）

（2）在添加作用域限定时需要**写上完整的类名和模板实参列表**

``` c++
template <class T, int kCapacity>
bool Stack<T,kCapacity>::empty() const{
    return _top == -1;
}
```



定义了这样一个类模板后，就可以去创建存放各种类型的栈

```` c++
Stack<int,20> stack2;
Stack<double,30> stack3;
Stack<string> stack; 
````



###  可变模板参数

可变模板参数( variadic templates )是 C++11 新增的最强大的特性之一，它对参数进行了高度泛化，它能表示0到任意个数、任意类型的参数。由于可变模版参数比较抽象，使用起来需要一定的技巧，所以它也是 C++11 中最难理解和掌握的特性之一。

可变参数模板和普通模板的语义是一样的，只是写法上稍有区别，声明可变参数模板时需要在typename 或 class 后面带上省略号 “...” 



```` c++
template <class... Args>
void func(Args... args);
````

==**Args叫做模板参数包，args叫做函数参数包**==



类比于C语言中的printf函数的参数个数可能有很多个，用...表示，参数的个数、类型、顺序可以随意，可以写0到任意个参数。

<img src="./cppbase.assets/image-20231106173252145.png" alt="image-20231106173252145" style="zoom:80%;" />



我们在定义一个函数时，可能有很多个不同类型的参数，不适合一一写出，所以提供了可变模板参数的方法。

定义一个可变模板参数

Args里面打包了 T1/T2/T3...这样的一些类型

args里面打包了函数的参数

**...在左边就是打包的含义**

利用可变参数模板输出参数包中参数的个数

```` c++
template <class ...Args> // Args 模板参数包
void display(Args ...args) // args 函数参数包
{
    // 输出模板参数包中类型参数个数
    cout << "sizeof...(Args) = " << sizeof...(Args) << endl;
    // 输出函数参数包中参数的个数
    cout << "sizeof...(args) = " << sizeof...(args) << endl;
}

void test0(){
    display();
    display(1,"hello",3.3,true);
}
````



——试验：希望打印出传入的参数的内容

就需要对参数包进行解包。每次解出第一个参数，然后递归调用函数模板，直到<span style=color:red;background:yellow>**递归出口**</span>



```` c++
// 递归的出口，递归的出口要放在可变参数模板的上面
void print(){
    cout << endl;
}

void print(int x){
    cout << x << endl;
}

// 重新定义一个可变参数模板，至少得有一个参数
template <class T,class ...Args>
void print(T x, Args ...args)
{
    cout << x << " ";
    print(args...);
}
````



如下所示，各种调用的步骤：

```` c++
void test1(){
    // 调用普通函数，不会调用函数模板，因为函数模板至少有一个参数
    print();

    // cout << 2.3 << " ";
    // cout << endl;
    print(2.3);

    // cout << 1 << " ";
    // print("hello",3.6,true);
    // cout << "hello" << " ";
    // print(3.6,true);
    // ...
    print(1,"hello",3.6,true);


    // 在剩下一个参数时结束递归
    print(1,"hello",3.6,true,100);
}
````



——想要输出类型



``` c++
void print(){
    cout << endl;
}

void print(int x){
    cout << x << endl;
}

//重新定义一个可变参数模板，至少得有一个参数
template <class T,class ...Args>
void print(T x, Args ...args)
{
    cout << typeid(x).name() << " ";
    print(args...);
}

print(1,"hello",3.6,true,100);
```

只剩下一个int型参数的时候，也没有使用函数模板，而是通过普通函数结束了递归。

关于递归出口，可以采用普通函数或者普通的函数模板，但是规范操作是使用普通函数。

（1）尽量避免函数模板之间的重载

（2）普通函数的优先级一定优于函数模板，更不容易出错



# 第十章 移动语义与智能指针

## 移动语义

为什么要用移动语义？

我们回顾一下之前模拟的String.cc

``` c++
class String
{
public:
	String(): _str(new char[1]()) {}
    
	String(const char* pstr)
    :_str(new char[strlen(pstr) + 1]()) 
    {
        strcpy(_str, pstr);
    }
    
	String(const String& rhs)
    :_str(new char[strlen(rhs.c_str()) + 1]())
	{
        strcpy(_str, rhs.c_str());
    }
    
	String &operator=(const String &rhs){
		if (this != &rhs){
			delete [] _str;
		    _str = new char[strlen(rhs.c_str()) + 1];
		    strcpy(_str, rhs.c_str());
		}
		return *this;
	}
    
	~String(){
		if (_str){
			delete [] _str;
			_str = nullptr;
		}
	}
    
private:
	char* _str;
};

void test0(){
    // 构造函数
    String s1("hello");
    
    // 拷贝构造
    String s2 = s1;
    
    // 先构造，再拷贝构造
    // 利用"hello"这个字符串创建了一个临时对象，并复制给了s3
    // 这一步实际上new了两次
    String s3 = "hello";
}
```

创建 s3 的过程中实际创建了一个临时对象，也会在堆空间上申请一片空间，然后把字符串内容复制给 s3 的pstr，这一行结束时临时对象的生命周期结束，它申请的那片空间被回收。这片空间申请了，又马上被回收，实际上可以视作一种不必要的开销。我们希望能够少new一次，可以直接将 s3 能够复用临时对象申请的空间。



### 左值与右值

左值和右值是针对表达式而言的，**左值**是指**表达式执行结束后依然存在的==持久对象==**，**右值**是指**表达式执行结束后就不再存在的==临时对象==**。

那如何进行区分呢？其实也简单，<u>能对表达式取地址的，称为左值；不能取地址的，称为右值</u>。

在实际使用过程中，**字面值常量**、**匿名对象（临时对象）**、**匿名变量（临时变量）**，都称为**右值**。右值又被称为即将被销毁的对象。



**字面值常量**：也就是10， 20这样的数字，属于**右值**，不能取地址。

**字符串常量**：“world"，是属于**左值**的，位于内存中的**文字常量区**。



试试看下面这些取址操作和引用绑定操作是否可行：

``` c++
void test1() {
	int a = 1, b = 2;
	&a;
	&b;
	// &(a + b); // error 临时变量（匿名变量） 右值
	// &10; // 右值
    &String("hello"); // 匿名对象
    String("hello").print(); // ok 声明周期在当前行
    String str("hello"); // 有名对象
    &str; // ok
    
    // 非const引用尝试绑定
	int & r1 = a;
	int & r2 = 1; // error 非const引用不能绑定右值
    
    // const引用尝试绑定
    // const引用可以绑定右值
	const int & r3 = 1;
	const int & r4 = a;
    
	String s1("hello");
	String s2("wangdao");
	&s1;
	&s2;
	&(s1 + s2); // 右值
}
```



> 如上定义的`int & r1 ` 和 `const int & r3` 叫作**左值引用**与**const左值引用**
>
> ==非const左值引用==只能绑定到**==左值==**，<u>不能绑定到右值</u>，也就是非const左值引用只能识别出左值。
>
> **==const左值引用==**既可以绑定到**==左值==**，也可以绑定到**==右值==**，也就是表明const左值引用不能区分是左值还是右值。
>
> 
>
> ——希望能够区分出右值，并且还要进行绑定
>
> 就是为了实现String s3 = "hello"的空间复用需求。



###  右值引用 &&

> C++11提出了新特性**右值引用**
>
> ==右值引用<u>不能绑定到左值</u>，但是**可以绑定到右值**==，也就是右值引用可以**识别出右值**
>
> ``` c++
> // 非const引用：不能绑定右值
> int & r1 = a;
> int & r2 = 1; // error
> 
> // const引用：既可以绑定左值，又可以绑定右值
> const int & r3 = 1;
> const int & r4 = a;
> 
> // 右值引用：只能绑定右值
> int && r_ref = 10;
> int && r_ref2 = a; // error
> ```
>
> 
>
> 右值引用本身是左值还是右值？
>
> —— 对r_ref取地址是可行的，r_ref本身是一个左值。但这并**不代表右值引用本身一定是左值**。
>
> **实际上，==右值引用既可以是左值（比如：作为函数的参数、有名字的变量），也可以是右值（函数的返回类型）==**。
>
> 这个问题，我们留到1.1.6章节再做讨论。



### 移动构造函数（重要）	`T(T && rhs)`

有了右值引用后，实际上再接收临时对象作为参数时就可以分辨出来。

之前 `String str1 = String("hello");` 这种操作调用的是**拷贝构造函数**，形参为 `const String &` 类型，既能绑定右值又能绑定左值。为了确保进行左值的复制时不出错，一律采用**重新开辟空间**的方式。

有了能够分辨出右值的右值引用之后，我们就可以定义一个新的构造函数了 —— <span style=color:red;background:yellow>**移动构造函数**</span> `String(String && rhs)`。

<img src="./cppbase.assets/undefined202403221006005.png" alt="image-20240322100627895" style="zoom: 50%;" />



给String类加上**移动构造函数**，在初始化列表中完成：

1. **==浅拷贝，使s3的pstr指向临时对象的pstr所指向的空间（复用）==**
2. **==将临时对象（右操作数）的pstr设为空指针，因为这个临时对象会马上销毁（要避免临时对象调用析构函数回收掉这片堆空间）==**

``` c++
String(String && rhs) // 传进来临时对象作为参数
: _pstr(rhs._pstr)	  // 浅拷贝
{
    cout << "String(String&&)" << endl;
    rhs._pstr = nullptr; // 要避免临时对象调用析构函数回收掉这片堆空间
}
```

再运行代码，加上**编译器的去优化参数** `-fno-elide-constructors`，发现：

`String s3 = "hello";`没有再调用拷贝构造函数，而是调用了**移动构造函数**。

`String str1 = String("hello");`这种操作调用的是**拷贝构造函数**。



> 移动构造函数的特点：
>
> 1. ==**移动构造函数**优于拷贝构造函数执行==（实际上绑定左值也会经历这个过程，但是移动构造函数中的右值引用不能绑定左值，所以采用了拷贝构造函数）。
>
> 2. 移动构造函数==如果不显式写出，编译器不会自动生成==。（错误）



###  移动赋值函数（重要）	`T & operator=(T && rhs)`

有了移动构造函数的成功经验，很容易想到原本的赋值运算符函数。

比如，我们进行如下操作时：

``` c++
String s3("hello");
s3 = String("wangdao");
```

原本赋值运算符函数的做法：

<img src="./cppbase.assets/image-20231107100059563.png" alt="image-20231107100059563" style="zoom: 67%;" />

我们希望复用临时对象申请的空间，那么也同样需要赋值运算符函数能够分辨出接收的参数是左值还是右值，同样可以利用右值引用。

<img src="./cppbase.assets/image-20231107100306135.png" alt="image-20231107100306135" style="zoom:67%;" />

再写出移动赋值函数（移动赋值运算符函数），优先级也是高于赋值运算符函数。

注意：**==自复制判断==**！**==自复制判断==**！**==自复制判断==**！**==自复制判断==**！**==自复制判断==**！**==自复制判断==**！

``` c++
String & operator=(String && rhs){
    if(this != &rhs){ // 自复制判断
        delete [] _pstr; //为什么要做自复制判断？因为需要先delete掉s3对象的堆空间，如果是自复制，下面在进行赋值就会报错
        // 浅拷贝
        _pstr = rhs._pstr;
        rhs._pstr = nullptr;
        cout << "String& operator=(String&&)" << endl;
    }
    return *this;
}
```



> **总结：**
>
> 将**<u>拷贝构造函数</u>**和**<u>赋值运算符函数</u>**称为**具有<u>复制控制语义</u>的函数**
>
> 将**==移动构造函数==**和**==移动赋值函数==**称为**具有==移动语义==的函数**
>
> <font color=red>**具有==移动语义==的函数==优于==具有==复制控制语义==的函数先执行；**</font>
>
> **具有移动语义的函数如果不显式写出，编译器不会自动生成，必须手写。**（错误）
>
> 
>
> ==C++11中新增的移动构造函数和移动赋值函数的生成条件如下：==
>
> - 移动构造函数的生成条件：没有自己实现移动构造函数，并且没有自己实现**==析构==函数**、**==拷贝==构造函数**和**==拷贝==赋值函数**。
> - 移动赋值重载函数的生成条件：没有自己实现移动赋值重载函数，并且没有自己实现**==析构==函数**、**==拷贝==构造函数**和**==拷贝==赋值函数**。
>
> 也就是说，移动构造和移动赋值的生成条件与之前六个默认成员函数不同，并不是单纯的没有实现移动构造和移动赋值编译器就会默认生成。



==思考：移动赋值函数中的**自复制判断**是否还有必要？==

```` c++
String s1("hello");
// 右值复制给左值，肯定不是同一个对象
s1 = String("world");
// 创建了两个内容相同的临时对象，也不是同一对象
String("wangdao") = String("wangdao");
````

似乎去掉自复制判断不会造成问题，但是c++11提出了一种方式，将左值转为右值，就是std::move()函数



> 补充：
>
> 在C++11之前，一个类中有如下六个默认成员函数：
>
> - 构造函数。
> - 析构函数。
> - 拷贝构造函数。
> - 拷贝赋值函数。
> - 取地址重载函数。
> - const取地址重载函数。



###  std::move()函数

在一些使用**移动语义**的场景下，有时需要将左值转为右值。std::move() 函数的作用是**==显式的将一个左值转换为右值==**，<span style=color:red;background:yellow>**其实现本质上就是一个强制转换**</span>。当将一个左值显式转换为右值后，<u>原来的左值对象就无法正常工作了，必须要**重新赋值**才可以继续使用</u>。

``` c++
void test() {
    int a = 1；
    &(std::move(a)); // error，左值转成了右值
    
	String s1("hello");
	cout << "s1:" << s1 << endl;
	String s2 = std::move(s1);
	cout << "s1:" << s1 << endl;
	cout << "s2:" << s2 << endl;
}
```

- 验证：如果**将移动赋值函数的==自复制判断去除==**，如下情况依然会调用移动赋值函数，==**但是s1的pstr所指向的空间被回收，且被设为了空指针**==，会出错。


``` c++
String str1("hello");
s1 = std::move(s1);
s1.print();
```

- 验证：**将移动赋值函数中的==浅拷贝去除==**，让左操作数s1 的 `_pstr`重新指向一片空间，后面对右操作数的 `_pstr` 设为空指针，但是依然造成了程序的中断，所以说明对 std::move(s1) 的内容进行修改，会导致s1的内容也被修改。


**std::move() 的本质是在底层做了强制转换（并不是像名字表面的意思一样做了移动）**。

``` c++
String & operator=(String && rhs){
    delete [] _pstr;
    _pstr = new char[1]();
    rhs._pstr = nullptr;
    cout << "String& operator=(String&&)" << endl;
    return *this;
}
```

—— 所以**==移动赋值函数的自复制判断不应该省略==**。

```c++
void test4(){
    String s1("hello"); 	// 两次构造函数
    s1 = String("world"); 	// 一次构造函数 一次移动赋值函数
    s1.print(); // world
    s1 = std::move(s1); 	// 一次移动赋值函数
    s1.print(); // world
}
```



### 右值引用本身的性质

我们来定义一个返回值是右值引用的函数：

``` c++
int && func(){
    return 10;
}

void test1(){
    // &func();  // 无法取址，说明返回的右值引用本身也是一个右值
    int && ref = func();
    &ref;  // 可以取址，此时ref是一个右值引用，其本身是左值
}
```



```c++
// 探讨右值引用
String func2(){
    String str1("wangdao");
	str1.print();
    return str1;
}

void test5(){
    // func2();
    // &func2(); // error，编译时出错，函数的返回值是右值
    String && ref = func2(); // ok
    &ref;  // 右值引用本身为左值
}
```



**右值引用本身是左值还是右值，取决于==是否有名字==，有名字就是左值，没名字就是右值。**

``` c++
String func2(){
    String str1("wangdao");
	str1.print();
    return str1;
}

void test2(){
    func2(); // 调用移动构造函数
    // &func2(); // error,右值
    String && ref = func2();
    &ref;  // 右值引用本身为左值
}
```



### 拷贝构造函数 和 移动构造函数 的调用时机

这里func2的调用按以前的理解会调用拷贝构造函数，但是发现结果是调用了移动构造函数。

调用时机：

- <font color=red>**当==返回的对象其生命周期即将结束==，就不再调用拷贝构造函数，而是调用==移动构造==函数。**</font>

- <font color=red>**如果==返回的对象声明周期大于func3()函数==，执行return语句时还是调用==拷贝构造==函数**</font>。

``` c++
String s10("beijing");

String func3(){
    s10.print();
    return s10;
}

void test3(){
    func3(); // 调用拷贝构造函数
}
```

总结：当类中同时定义移动构造函数和拷贝构造函数，需要对以前的规则进行补充，**调用哪个函数还需要取决于返回的对象的生命周期**。



## 资源管理

C语言在进行资源管理的时候，比如文件指针，由于分支较多，或者由于写代码的人与维护的人不一致，导致分支没有写的那么完善，从而导致文件指针没有释放。

```C
void UseFile(char const* fn) {
	FILE* f = fopen(fn, “r”); // 1. 获取资源
	// …… // 2.使用资源
	// 回收资源有很多分支
	if (!g()) { fclose(f); return; }
	// ...
	if (!h()) { fclose(f); return; }
	// ...
	fclose(f); // 释放资源
}
```

根据之前单例对象自动释放的经验，我们可以想到利用对象的生命周期去管理资源。那么就可以尝试实现一个安全回收文件的程序了。

``` c++
class SafeFile
{
public:
    // 在构造函数中初始化资源（托管资源）
    SafeFile(FILE * fp)
    : _fp(fp)
    {
        cout << "SafeFile(FILE*) " << endl;
    }
    // 提供方法访问资源
    void write(const string & msg){
        fwrite(msg.c_str(),1,msg.size(),_fp);
    }
    // 利用析构函数释放资源
    ~SafeFile(){
        cout << "~SafeFile()" << endl;
        if(_fp){
            /* fclose(_fp); */
            cout << "fclose(_fp)" << endl;
        }
    }
private:
    FILE * _fp;
};

void test0(){
    string msg = "hello,world";
    SafeFile sf(fopen("wd.txt","a+"));
    sf.write(msg);
}
```



###  RAII技术

以上例子其实已经用到了RAII的技术。所谓RAII，是C++提出的**资源管理的技术**，全称为Resource Acquisition Is Initialization，由C++之父Bjarne Stroustrup提出。其本质是**==利用对象的生命周期来管理资源==**（内存资源、文件描述符、文件、锁等），因为当对象的生命周期结束时，会自动调用析构函数。



#### RAII类的常见特征

RAII技术，具备以下基本特征：

- **==在构造函数中初始化资源==**，或托管资源。

- **==在析构函数中释放资源==**。

- 一般**不允许进行复制或者赋值（==对象语义==）**。

- **==提供若干访问资源的方法==**（如：读写文件）。

> 对象语义与值语义：
>
> - **==值语义==：可以进行复制或赋值**（两个变量的值可以相同）
>
> ``` c++
> int a = 10; int b = a; int c = 20;
> c = a; // 赋值
> int d = c; // 复制
> ```
>
> 
>
> - **==对象语义==：不允许复制或者赋值**
>
> （全世界不会有两个完全一样的人，程序世界中也不会有两个完全一样的对象）
>
> **常用手段：**
>
> 1. 将<u>拷贝构造函数</u>与<u>赋值运算符函数</u>设置为**==私有==**的
> 2. 将<u>拷贝构造函数</u>与<u>赋值运算符函数</u>**===delete==**
> 3. 使用继承的思想，将==基类的<u>拷贝构造函数</u>与<u>赋值运算符函数</u>**删除**（或**设为私有**）==，让派生类继承基类



#### RAII类的模拟

我们可以实现以下的一个类，模拟RAII的思想

``` c++
template <class T>
class RAII
{
public:
    // 1.在构造函数中初始化资源（托管资源）
    RAII(T * data)
    : _data(data)
    {
        cout << "RAII(T*)" << endl;
    }

    // 2.在析构函数中释放资源
    ~RAII(){
        cout << "~RAII()" << endl;
        if(_data){
            delete _data;
            _data = nullptr;
        }
    }

    // ===============================3.提供访问资源的方法==============================
    T * operator->(){   // 返回类型T的指针
        return _data;
    }

    T & operator*(){    // T& 函数的返回结果是一个左值
        return *_data;  // 返回_data指针指向的内容，_data是一个T*类型的指针
    }
    
    // 通过对象获取到裸指针
    T * get() const{
        return _data;   // 返回T*类型的_data，_data是存储一个该类型对象的地址
    }

    // 重新接管新的资源
    void set(T * data){ // 接受一个T类型的指针作为参数
        if(_data){
            delete _data;
            _data = nullptr;
        }
        _data = data;   // 将新的资源指针 data 赋值给 _data
    }

    // 4.不允许复制或赋值
    RAII(const RAII & rhs) = delete;
    RAII& operator=(const RAII & rhs) = delete;
    
private:
    T * _data;
};
```



如下，pt 不是一个指针，而是一个对象，但是它的使用已经和指针完全一致了。这个对象可以托管堆上的Point对象，而且不用考虑delete。

``` c++
void test0() {
	Point * pt = new Point(1, 2);
	// 智能指针的雏形
	RAII<Point> raii(pt);
	raii->print();
	(*raii).print();
}
```



```c++
void test0(){
    RAII<int> raii(new int(10)); // RAII(T*)

    // Point * p2 = new Point(1,2);
    // p2->print();
    // (*p2).print();
    // delete p2;

    RAII<Point> raii2(new Point(3,8)); // Point(int=0,int=0) RAII(T*)
    raii2->print(); // (3,8)
    (*raii2.get()).print(); // (3,8)
    raii2.set(new Point(4,5));
    (*raii2).print(); // (4,5)

    int * p = new int(20);  // 堆对象
    cout << *p << endl; // 20
    delete p; // 如果不delete漏4B
}
```

RAII技术的本质：==利用**栈对象**的生命周期管理资源==，因为**栈对象在离开作用域时候，会执行析构函数**。



###  智能指针

c++11提供了以下几种智能指针，位于**==头文件`<memory>`==**，它们都是类模板。

```C++
std::auto_ptr		c++0x
std::unique_ptr		c++11
std::shared_ptr		c++11
std::weak_ptr		c++11
```



#### auto_ptr的使用

auto_ptr 是最简单的智能指针，使用上存在缺陷，已经被 C++17 弃用了。

auto_ptr 是==有**复制**、**赋值**函数==的。

``` c++
void test0(){
    int * pInt = new int(10);
    // 创建auto_ptr对象接管资源
    auto_ptr<int> ap(pInt);
    cout << "*pInt:" << *pInt << endl;
    cout << "*ap:" << *ap << endl;
}
```

尽管会有 warning 提示，代码仍可通过。发现不用对pInt进行delete，也没有内存泄露。

auto_ptr 可以进行复制，但是存在隐患

``` c++
auto_ptr<int> ap2(ap);
cout << "*ap2:" << *ap2 << endl; // ok
cout << "*ap:" << *ap << endl;  
```

当ap2复制了ap后，对ap2管理的资源进行访问没有问题，但是对ap解引用会导致段错误。

```
void test0(){
    int * pInt = new int(10);
    auto_ptr<int> ap(pInt);

    // 二选一
    // auto_ptr<int> ap2(pInt); // double free
    auto_ptr<int> ap2(ap); // Segmentation fault
    
    cout << "*ap2:" << *ap2 << endl;
    cout << "*pInt:" << *pInt << endl;

    // 猜测：通过auto_ptr的拷贝构造
    // 从ap拷贝出ap2对象，实际是一个控制权移交的过程
    // ap的指针被置空了
    cout << "*ap:" << *ap << endl;
}
```

通过阅读源码的实现，ap的指针被置为了空指针。

``` c++
template <class _Tp> 
class auto_ptr {
public:
   auto_ptr(auto_ptr& __a) __STL_NOTHROW 
   : _M_ptr(__a.release()) 
   {}

   _Tp* release() __STL_NOTHROW 
   {
    _Tp* __tmp = _M_ptr;
    _M_ptr = nullptr;
    return __tmp;
  }
    
private:
  _Tp* _M_ptr;
};
```

也就是说，`auto_ptr<int> ap2(ap)； `这一步==表面上执行了拷贝操作，但是底层已经将右操作数ap所托管的堆空间的控制权交给了左操作数ap2，并且将ap底层的指针数据成员置空==。该拷贝操作存在隐患，所以auto_ptr被弃用了。

```c++
int * pInt = new int(10);
auto_ptr<int> ap(pInt);
/* auto_ptr<int> ap2(pInt); */

auto_ptr<int> ap2(ap);
cout << "*ap2:" << *ap2 << endl;
cout << "*pInt:" << *pInt << endl;
// 猜测：通过auto_ptr的拷贝构造
// 从ap拷贝出ap2对象
// 实际是一个控制权移交的过程
// ap的指针被置空了
cout << "*ap:" << *ap << endl;
```



#### ==unique_ptr的使用（重要）==

unique_ptr 对 auto_ptr 进行了改进。

- **特点1：==不允许==复制或者赋值**，具备**==对象语义==**。

- **特点2：==独享所有权==的智能指针**。

``` c++
void test0(){
    unique_ptr<int> up(new int(10));
    cout << "*up:" << *up << endl; // =================获取值
    cout << "up.get(): " << up.get() << endl; // ======获取地址

    // 独享所有权的智能指针，对托管的空间独立拥有(不允许赋值或赋值)
    // ---------------- 拷贝构造已经被删除 ------------------
    // unique_ptr<int> up2 = up; // (复制)操作 error
    // ---------------- 赋值运算符函数也被删除 --------------
    unique_ptr<int> up3(new int(20));
    // up3 = up; // (赋值)操作 error
}
```

将 auto_ptr 的缺陷摒弃了，具有对象语义，语法层面不允许复制、赋值。

- **特点3：作为==容器==元素**。

要利用**移动语义**的特点，可以==直接传递 unique_ptr 的**右值**==，构建右值的方式有：

1、**std::move** 的方式。

2、可以**直接使用 unique_ptr 的构造函数**，创建匿名对象（临时对象），构建右值。

``` c++
void test(){
	vector<unique_ptr<Point>> vec;
    unique_ptr<Point> up4(new Point(10,20));
    // up4是一个左值，将up4这个对象作为参数传给了push_back函数，会调用拷贝构造
    // 但是unique_ptr的拷贝构造已经删除了，所以这样写会报错
    // vec.push_back(up4); // error 编译报错 use of deleted function
    vec.push_back(std::move(up4)); // ok
    vec.push_back(unique_ptr<Point>(new Point(1,3))); // ok   
}
```



#### ==shared_ptr的使用（重要）==

智能指针独享资源的控制权固然是一种需求，但有些场景下也需要允许共享控制权。

shared_ptr 就是共享所有权的智能指针，**可以进行复制或赋值，但复制或赋值时，并不是真正拷贝对象，而只是将引用计数加1**。即 shared_ptr 引入了引用计数，其思想与COW技术类似，又称为是强引用的智能指针。

- **特征1：==共享所有权==的智能指针**，可以使用**==引用计数==**记录对象的个数。
- **特征2：==可以==进行复制或者赋值**，表明具备**==值语义==**。
- **特征3：也可以作为==容器==的元素**，作为容器元素的时候，即可以传递左值，也可以传递右值。（区别于unique_ptr只能传右值）
- **特征4：也具备==移动语义==**，表明也有移动构造函数与移动赋值函数。

``` c++
shared_ptr<int> sp(new int(10));
cout << "sp.use_count(): " << sp.use_count() << endl; // 1

cout << "执行复制操作" << endl;
shared_ptr<int> sp2 = sp;
cout << "sp.use_count(): " << sp.use_count() << endl; // 2
cout << "sp2.use_count(): " << sp2.use_count() << endl; // 2

cout << "再创建一个对象sp3" << endl;
shared_ptr<int> sp3(new int(30));
cout << "sp.use_count(): " << sp.use_count() << endl; // 2
cout << "sp2.use_count(): " << sp2.use_count() << endl; // 2
cout << "sp3.use_count(): " << sp3.use_count() << endl; // 1

cout << "执行赋值操作" << endl;
sp3 = sp;
cout << "sp.use_count(): " << sp.use_count() << endl; // 3
cout << "sp2.use_count(): " << sp2.use_count() << endl; // 3
cout << "sp3.use_count(): " << sp3.use_count() << endl; // 3
cout << "*sp:" << *sp << endl; 
cout << "*sp2:" << *sp2 << endl;
cout << "*sp3:" << *sp3 << endl;
cout << "sp.get():" << sp.get() << endl; 
cout << "sp2.get():" << sp2.get() << endl; 
cout << "sp3.get():" << sp3.get() << endl; 
```



```c++
void test0(){
    shared_ptr<int> sp(new int(10));
    // cout << "*sp: " << *sp << endl;
    // cout << sp.get() << endl;

    // cout << endl;
    cout << "sp.use_count(): " << sp.use_count() << endl; // 1
    
    //复制操作    
    shared_ptr<int> sp2 = sp;
    
    // cout << "*sp:" << *sp << endl;
    // cout << sp.get() << endl;
    // cout << "*sp2:" << *sp2 << endl;
    // cout << sp2.get() << endl;
    cout << "sp.use_count(): " << sp.use_count() << endl; // 2
    cout << "sp2.use_count(): " << sp2.use_count() << endl; // 2

    cout << endl;
    // 赋值操作
    shared_ptr<int> sp3(new int(20));
    sp2 = sp3;

    cout << "sp.use_count(): " << sp.use_count() << endl; // 减一变为1
    cout << "sp2.use_count(): " << sp2.use_count() << endl; // 2
    cout << "sp3.use_count(): " << sp3.use_count() << endl; // 2

    // 作为容器元素
    vector<shared_ptr<int>> vec;
    vec.push_back(sp);
    vec.push_back(std::move(sp2));
}
```



#### shared_ptr的循环引用

[share_ptr循环引用产生原因及其解决方案](https://blog.csdn.net/weixin_45880571/article/details/119345415)

shared_ptr还存在一个问题 —— 循环引用问题。

我们建立一个Parent和Child类的一个结构

```c++
class Child;

class Parent
{
public:
    Parent()
    { cout << "Parent()" << endl; }

    ~Parent()
    { cout << "~Parent()" << endl; }

    // 只需要Child类型的指针，不需要类的完整定义
    shared_ptr<Child> spChild;
};

class Child
{
public:
    Child()
    { cout << "child()" << endl; }

    ~Child()
    { cout << "~child()" << endl; }

    shared_ptr<Parent> spParent;
};
```



由于shared_ptr的实现使用了引用计数，那么如果进行如下的创建

- **==use_count() 可以查看引用计数==**。

```c++
shared_ptr<Parent> parentPtr(new Parent());
shared_ptr<Child> childPtr(new Child());
// 获取到的引用计数都是1
cout << "parentPtr.use_count():" << parentPtr.use_count() << endl;
cout << "childPtr.use_count():" << childPtr.use_count() << endl;
```

<img src="./cppbase.assets/undefined202403222027179.png" alt="image-20240322202756063" style="zoom: 50%;" />

——程序结束时，发现 Parent 和 child 的析构函数都没有被调用

```c++
shared_ptr<Parent> parentPtr(new Parent());
shared_ptr<Child> childPtr(new Child());
// 获取到的引用计数都是1
cout << "parentPtr.use_count():" << parentPtr.use_count() << endl;
cout << "childPtr.use_count():" << childPtr.use_count() << endl;
parentPtr->spChild = childPtr;
childPtr->spParent = parentPtr;
// 获取到的引用计数都是2
cout << "parentPtr.use_count():" << parentPtr.use_count() << endl;
cout << "childPtr.use_count():" << childPtr.use_count() << endl;
```

<img src="./cppbase.assets/image-20231107161838102.png" alt="image-20231107161838102" style="zoom: 67%;" />

childPtr 和 parentPtr 会先后销毁，但是堆上的 Parent 对象和 Child 对象的引用计数都变成了1，而不会减到0，所以没有回收。

<img src="./cppbase.assets/image-20231107163016399.png" alt="image-20231107163016399" style="zoom:67%;" />

07_shared_ptr.cc

```c++
#include <iostream>
#include <memory>
using std::cout;
using std::endl;
using std::shared_ptr;
using std::weak_ptr;

class Child;

class Parent
{
public:
    Parent()
    { cout << "Parent()" << endl; }

    ~Parent()
    { cout << "~Parent()" << endl; }
    
    //只需要Child类型的指针，不需要类的完整定义
    shared_ptr<Child> _spParent;
};

class Child
{
public:
    Child()
    { cout << "child()" << endl; }

    ~Child()
    { cout << "~child()" << endl; }
    
    shared_ptr<Parent> _spParent;
};

void test0(){
    shared_ptr<Parent> parentPtr(new Parent());
    shared_ptr<Child> childPtr(new Child());
    // 获取到的引用计数都是1
    cout << "parentPtr.use_count():" << parentPtr.use_count() << endl;
    cout << "childPtr.use_count():" << childPtr.use_count() << endl;

    // parentPtr是一个管理Parent对象的智能指针，可以利用箭头运算符访问它所管理的Parent对象的成员
    // _spChild就是Parent对象的成员，同时也是一个能够管理Child对象的智能指针
    // 因为shared_ptr类型的智能指针可以进行赋值操作，所以可以使_spChild也能管理childPtr所管理的对象
    
    parentPtr->_spParent = childPtr;
    childPtr->_spParent = parentPtr;
    // 获取到的引用计数都是2
    cout << "parentPtr.use_count():" << parentPtr.use_count() << endl;
    cout << "childPtr.use_count():" << childPtr.use_count() << endl;
}

int main(void){
    test0();
    return 0;
}
```



##### 解决思路

希望某一个指针指向一片空间，能够指向，但是不会使引用计数加1，那么堆上的Parent对象和Child对象必然有一个的引用计数是1，栈对象再销毁的时候，就可以使引用计数减为 0。

shared_ptr 无法实现这一效果，所以引入了 `weak_ptr`。

weak_ptr是一个**弱引用**的智能指针，不会增加引用计数。

shared_ptr是一个**强引用**的智能指针。

- 强引用，指向一定会增加引用计数，只要有一个引用存在，对象就不能释放；

- 弱引用并不增加对象的引用计数，==但是**它知道所托管的对象是否还存活**==。


——循环引用的解法，将 Parent 类或 Child 类中的任意一个 shared_ptr 换成 weak_ptr 类型的智能指针

比如：将 Parent 类中的 shared_ptr 类型指针换成 weak_ptr

<img src="./cppbase.assets/undefined202403222029511.png" alt="image-20240322202959393" style="zoom:50%;" />

栈上的childPtr对象先销毁，会使堆上的Child对象的引用计数减1，因为这个Child对象的引用计数本来就是1，所以减为了0，回收这个Child对象，造成堆上的Parent对象的引用计数也减1。

再当parentPtr销毁时，会再让堆上的Parent对象的引用计数减1，所以也能够回收。

<img src="./cppbase.assets/undefined202403222035106.png" alt="image-20240322203519008" style="zoom:50%;" />

<img src="./cppbase.assets/undefined202403222035411.png" alt="image-20240322203500311" style="zoom:50%;" />



```c++
#include <iostream>
#include <memory>
using std::cout;
using std::endl;
using std::shared_ptr;
using std::weak_ptr;
class Child;
class Parent
{
public:
    Parent()
    { cout << "Parent()" << endl; }

    ~Parent()
    { cout << "~Parent()" << endl; }
    
    weak_ptr<Child> _wpChild; // ========== 注意 ==========
};

class Child
{
public:
    Child()
    { cout << "child()" << endl; }

    ~Child()
    { cout << "~child()" << endl; }
    
    shared_ptr<Parent> _spParent;
};

void test0(){
    shared_ptr<Parent> parentPtr(new Parent());
    shared_ptr<Child> childPtr(new Child());
    cout << parentPtr.use_count() << endl; // 1
    cout << childPtr.use_count() << endl; // 1

    parentPtr->_wpChild = childPtr;
    childPtr->_spParent = parentPtr;
    cout << "parentPtr.use_count():" << parentPtr.use_count() << endl; // 2
    cout << "childPtr.use_count():" << childPtr.use_count() << endl; // 1
}

int main(void){
    test0();
    return 0;
}
```



#### weak_ptr的使用

weak_ptr 是弱引用的智能指针，它是 shared_ptr 的一个补充，使用它进行复制或者赋值时，并**==不会导致引用计数加1==**，是为了解决 shared_ptr 的问题而诞生的。

weak_ptr 知道所托管的对象是否还存活，如果存活，**必须要提升为 shared_ptr 才能对资源进行访问，不能直接访问**。

##### weak_ptr 初始化

```c++
// 1. 无参的方式创建weak_ptr
weak_ptr<int> wp;

// 2. 也可以利用shared_ptr创建weak_ptr 
weak_ptr<int> wp2(sp);

// 不能这样创建
// weak_ptr<int> wp2(new int(20)); // error
```

##### 判断关联的空间是否还在

```c++
// ================= shared_ptr可以直接判断 ==================
if(sp){
	cout << "有一片空间" << endl;
}

// ============= weak_ptr不能直接判断 ===================
// if(wp){} // error
```

1. **可以直接使用 ==use_count() 函数==**

如果 use_count 的返回值大于0，表明关联的空间还在

```c++
shared_ptr<int> sp(new int(10));
weak_ptr<int> wp;
wp = sp;
shared_ptr<int> sp2 = sp;
cout << wp.use_count() << endl; // 2
```

2. **==lock() 函数==将 weak_ptr 提升为 shared_ptr**

下面这种==**赋值操作**可以让 wp 也能够托管这片空间，但是它作为一个 weak_ptr 仍**不能够去管理**，甚至不允许访问==（weak_ptr不支持直接解引用）。

``` c++
shared_ptr<int> sp(new int(10));
weak_ptr<int> wp; // 无参的方式创建weak_ptr
wp = sp; // =============== 赋值 ===============
cout << *sp << endl; // ok
// cout << *wp << endl; // error 必须要提升为shared_ptr才能对资源进行访问，不能直接访问
```

想要真正地去进行管理需要使用 **lock() 函数**将 weak_ptr 提升为 shared_ptr。

``` c++
shared_ptr<int> sp3 = wp.lock(); // 使用lock函数将weak_ptr提升为shared_ptr
if(sp3){
    cout << "提升成功" << endl;
    cout << *sp3 << endl;
}else{
    cout << "提升失败，没有托管的空间" << endl;
}
```

**==如果托管的资源没有被销毁，就可以成功提升为 shared_ptr，否则就会返回一个空的 shared_ptr（空指针）==**。

——查看 lock 函数的说明

```` c++
std::shared_ptr<T> lock() const noexcept;
// 将weak_ptr提升成一个shared_ptr，然后再来判断shared_ptr,进而知道weak_ptr指向的空间还在不在
````

3. **可以使用 ==expired() 函数==**

```c++
bool expired() const noexcept; // weak_ptr调用expired()函数去判断托管的资源有没有被回收
```

该函数返回 true 等价于 use_count() == 0

```c++
bool flag = wp.expired();
if(flag){
	cout << "托管的空间已经被销毁" << endl;
}else{
	cout << "托管的空间还在" << endl;
}
```

代码：

```c++
#include <iostream>
#include <memory>
using std::cout;
using std::endl;
using std::weak_ptr;
using std::shared_ptr;

void test0(){
	shared_ptr<int> sp(new int(10));
	weak_ptr<int> wp;
	// 不能这样创建
  	// weak_ptr<int> wp2(new int(20));

 	// 赋值
  	wp = sp;
  	shared_ptr<int> sp2 = sp;
 	cout << wp.use_count() << endl;
  	cout << sp.use_count() << endl;
  	cout << sp2.use_count() << endl;

  	if(sp){
        cout << "有一片空间" << endl;
  	}

  	// 不允许
    // if(wp){} // error

 	cout << *sp << endl;
  	// cout << *wp << endl; // error 必须要提升为shared_ptr才能对资源进行访问，不能直接访问
 	shared_ptr<int> sp3 = wp.lock(); // 使用lock函数将weak_ptr提升为shared_ptr
  	if(sp3){
        cout << "提升成功" << endl;
        cout << *sp3 << endl;
  	}else{
    	cout << "提升失败，没有托管的空间" << endl;
    }

  	cout << endl;
    // expired()返回true代表没有空间被托管
  	// 返回false代表有空间被托管
 	if(!wp.expired()){
      	cout << "有空间" << endl;
  	}else{
      	cout << "没有空间" << endl;
  	}
}

int main(void){
	test0();
	return 0;
}
```



### 智能指针删除器

很多时候我们都**==用 new 来申请空间==**，用 delete 来释放。库中实现的各种智能指针，默认也都是用 delete 来释放空间。

但是若我们**==采用malloc申请的空间==**或是**==用fopen打开的文件==**，这时智能指针的默认处理方式就不能解决了，必须**为智能指针定制删除器**，这样，我们的智能指针就可以定制化释放资源的方式了。

#### unique_ptr对应的删除器

<img src="./cppbase.assets/image-20231107174351960.png" alt="image-20231107174351960" style="zoom: 80%;" />

定义unique_ptr时，**如果没有指定删除器参数，就会使用==默认的删除器==**。点开std::default_delete的说明

<img src="./cppbase.assets/image-20231107174454514.png" alt="image-20231107174454514" style="zoom:80%;" />

英文版：

<img src="./cppbase.assets/image-20240325202858607.png" alt="image-20240325202858607" style="zoom:80%;" />

**==无论接管的是什么类型的资源，回收时都是会执行delete语句或delete [ ]==**



看下面这个例子，利用unique_ptr管理文件资源，出现问题

``` c++
void test0(){
    string msg = "hello,world\n";
    FILE * fp = fopen("res1.txt","a+");
    fwrite(msg.c_str(),1,msg.size(),fp);
    fclose(fp);
}

void test1(){
    string msg = "hello,world\n";
    unique_ptr<FILE> up(fopen("res2.txt","a+")); // ====== 定义智能指针的时候，没有传自定义的删除器 =====
    fwrite(msg.c_str(),1,msg.size(),up.get()); // get函数可以从智能指针中获取到裸指针
    // fclose(up.get()); // 可以注释，因为可以使用 unique_ptr 默认的删除器
}
```

**问题的原因：**

接管文件资源时，也使用了 delete 语句，导致错误（memcheck ./a.out发现很多报告）

——需要自定义删除器

仿照参考文档上默认删除器的示例，创建一个代表删除器的 struct，定义 operator() 函数。

``` c++
struct FILECloser{
    void operator()(FILE * fp){
        if(fp){
            fclose(fp);
            cout << "fclose(fp)" << endl;
        }
    }
};
```

创建 unique_ptr 接管文件资源时，删除器参数使用我们自定义的删除器。

``` c++
void test1(){
    string msg = "hello,world\n";
    unique_ptr<FILE, FILECloser> up(fopen("res2.txt","a+")); // ========== 传入自定义的删除器 ===========
    fwrite(msg.c_str(),1,msg.size(),up.get()); // get函数可以从智能指针中获取到裸指针
}
```

如果管理的是普通的资源，不需要写出删除器，就使用默认的删除器即可，**只有针对==FILE==或者==socket==这一类创建的资源，才需要改写删除器，使用fclose之类的函数**。



#### shared_ptr对应的删除器

**unique_ptr 和 shared_ptr 区别：**

- 对于unique_ptr，删除器是模板参数


<img src="./cppbase.assets/image-20231107201437584.png" alt="image-20231107201437584" style="zoom:80%;" />

- 对于shared_ptr，删除器是构造函数参数


<img src="./cppbase.assets/image-20231107201537512.png" alt="image-20231107201537512" style="zoom:80%;" />

所以**==传入删除器参数的位置不同==**。

``` c++
void test2(){
    string msg = "hello,world\n";
    FILECloser fc;
    // 在shared_ptr的构造函数参数中加入删除器对象
    shared_ptr<FILE> sp(fopen("res3.txt","a+"),fc); // ============= 传入删除器的位置不同 =============
    fwrite(msg.c_str(),1,msg.size(),sp.get());
}
```

- 位置不同：

<img src="./cppbase.assets/undefined202403221743551.png" alt="image-20240322174347471" style="zoom:67%;" />



###  智能指针的误用

智能指针被误用的情况，==原因都是将一个原生裸指针**交给了不同的智能指针进行托管**，而造成一个对象被销毁两次==。

对于 shared_ptr 与 unique_ptr 都会产生这个问题。

- unique_ptr 要注意的误用


``` c++
void test0(){
    //需要人为注意避免
    Point * pt = new Point(1,2);
 	unique_ptr<Point> up(pt);
 	unique_ptr<Point> up2(pt);
}

void test1(){
    unique_ptr<Point> up(new Point(1,2));
    unique_ptr<Point> up2(new Point(1,2));
 	//让两个unique_ptr对象托管了同一片空间
 	up.reset(up2.get());
}
```

<img src="./cppbase.assets/undefined202403221752838.png" alt="image-20240322175206762" style="zoom:67%;" />

- shared_ptr 要注意的误用

使用不同的智能指针托管同一片堆空间，即使是 shared_ptr 也是不行的。

==之前进行的 shared_ptr 的复制、赋值的参数都是 shared_ptr 的对象，不能直接多次把同一个裸指针传给它的构造==。

```c++
void test2() {
	Point * pt = new Point(10,20);
	shared_ptr<Point> sp(pt);
  	shared_ptr<Point> sp2(pt);
}

void test3() {
    //使用不同的智能指针托管同一片堆空间
  	shared_ptr<Point> sp(new Point(1,2));
  	shared_ptr<Point> sp2(new Point(1,2));
  	sp.reset(sp2.get());
}
```

- 还有一种误用


给 Point 类加入了这样的成员函数：

``` c++
Point * addPoint(Point * pt){
    _ix += pt->_ix;
    _iy += pt->_iy;
    return this;
}
```

使用时，这样还是使得 sp3 和 sp 同时托管了同一个堆对象。

```` c++
shared_ptr<Point> sp(new Point(1,2));    
shared_ptr<Point> sp2(new Point(3,4));

// 创建sp3的参数实际上是sp所对应的裸指针，效果还是多个智能指针托管了同一块空间
shared_ptr<Point> sp3(sp->addPoint(sp2.get())); // ======== 注意 ========
cout << "sp3 = ";
sp3->print();
````

——需要给sp3的构造函数传入`shared_ptr<Point>` 对象，而不是裸指针

**==解决思路==**：**通过 this 指针获取本对象的 shared_ptr**。

可以修改 Point 中的 addPoint 函数

``` c++
shared_ptr<Point> addPoint(Point * pt) {
    _ix += pt->_ix;
    _iy += pt->_iy;
    return shared_ptr<Point>(this); 
}
```

但是这样写，在 addPoint 函数中创建的匿名智能指针对象接收的还是 sp 对应的裸指针，那么这个匿名对象和 sp 所托管的空间还是同一片空间。匿名对象销毁时会 delete 一次，sp 销毁时又会 delete 一次。

——使用智能指针辅助类 enable_shared_from_this 的成员函数 shared_from_this。

![image-20231107205809125](./cppbase.assets/image-20231107205809125.png)

![image-20231107205839620](./cppbase.assets/image-20231107205839620.png)

在 Point 的 addPoint 函数中需要使用 shared_from_this 函数返回的 shared_ptr 作为返回值，要想在 Point 类中调用enable_shared_from_this 的成员函数，最佳方案可以让 Point 类继承 enable_shared_from_this 类。

这样修改 addPoint 函数后，问题解决。

``` c++
class Point 
: public std::enable_shared_from_this<Point> 
{
public:
	//...
	shared_ptr<Point> addPoint(Point & pt) {
		_ix += pt._ix;
		_iy += pt._iy;
		return shared_from_this();
	}
};
```



<img src="./cppbase.assets/undefined202403221816756.png" alt="image-20240322181651703" style="zoom:67%;" />

<img src="./cppbase.assets/undefined202403221816419.png" alt="image-20240322181616356" style="zoom: 67%;" />



**总结：智能指针的误用全都是==使用了不同的智能指针托管了同一块堆空间（同一个裸指针）==。**
