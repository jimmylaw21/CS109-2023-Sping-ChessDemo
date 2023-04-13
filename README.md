# CS109-2023-Sping-ChessDemo

### Author Information

Name: Haoyu Luo

SID: 12112517

Major: Computer Science and Technology

School: Southern University of Science and Technology

Email: [12112517@mail.sustech.edu.cn](mailto:12112517@mail.sustech.edu.cn)

### Preface

这是一个SUSTech CS109-2023-Sping课程project的一个样品，在实现基础功能的基础上实现了一些bonus功能，包括但不限于一个包含主界面，排行榜，游戏模式选择在内的多人游戏平台，更漂亮的GUI设计和动画效果，展示可行区域，复盘动画，无限制悔棋，简单的随机AI。(更高级的AI和联网游戏可能会出现在未来的某一天LOL)

正在上这门课的同学们注意，这只是一个样品，任何的直接抄袭行为将会面临查重系统的严格审核，所以为了你的信誉和成绩，可以参考一些功能的实现思路，但是请不要直接使用里面的任何代码片段。

![image](https://user-images.githubusercontent.com/95002380/231815848-576dcac9-c64e-4147-b24a-33062e985a2f.png)

### Project Requirements Analysis

**Task 1: Game Initialization (10 pts)**

- Your program should be able to initialize a new chess game, with the board elements displayed correctly, including board size, terrain types, quantities and locations, and pieces types, quantities and locations.
- 根据规则初始化view和model里面的各个组件

- Your program should be able to display the game state correctly, including the number of rounds and the current player. The terrain and pieces of both sides should be distinguished.
- 通过 controller 里的 swapcolor方法 修改 ChessGameBoard 的 label
- Your program should be able to restart by clicking a button instead of closing it and reopening the game again. The board elements and game state are displayed correctly after reset.
- restart 功能可以复用initialize方法，但是要注意所有东西需要被合理地重置。

**Task 2: Saving/Loading a Game (20 pts)**

-  Your program should be able to load an existing game from a text file with a pre-defined format by clicking buttons. After loading, all piece should be placed at the positions given in the text file. The file saving the game should include at least the current chessboard, the previous moves and the current side to play (red or blue).
-  保存整个棋盘，或者只保存移动步骤（新设计一种数据结构？）？
-  Your program should be able to save the current game into a text file.
-  java.io各种流
-  Your program should be able to perform error checking of saves can be done, such as any move is invalid, chessboard size is incorrect e.t.c.
-  读取文件时加上各种检测？答辩前记得准备一些错误文件。

**Task 3: Game Play (40 pts)**

- Your program should detect the winning status of the game, and end the game when there is a winner.
- 在执行一步后，判断各种胜利情况？

- Your program should allow pieces to move according to the terrains and rules, including some special rules of rats, tigers and lions.

- 格局规则仔细写，建议做完显示可行格子的bonus后，使用这一功能回来检查一下有没有疏漏。

**Task 4: Graphical Interface (10 pts)**

- Your program should have a graphical user interface using Java Swing.

    - JavaFX is acceptable, but it is not allowed to use WebView in JavaFX and implement the GUI with HTML+CSS+JavaScript. You are required to use Java to implement the drawing logic of the interface instead of web programming.

- 善用搜索引擎

**Task 5: Advanced Requirements (20/30 pts)**

- Design a platform for your game, such as adding multi-user, ranking list, adding start menu for selecting the game modes, etc.
- 设计更多种Frame，合理安排各个功能组件的位置；设计注册登录系统，增加胜局统计；展示排行榜时即时排序，或者使用堆来储存用户？
-  Make your game looks nicer, such as using beautiful images as chessboard, changing the theme, adding sound effect, adding background music, adding more prompt label when the game is in process.
- 研究怎么在容器中添加/替换图片；设计一个线程类来使用音乐库来播放音乐；overwrite那些你觉得不美观的框架组件？
- Show possible moves when a piece is selected.
- 复用isValidMove和isValidCapture
- Play the process of moving, capturing, and flipping pieces after loading the save file.
- 一步步读取文件来实现复盘，点击式或者动画式(注意repaint的机制，换成paintImmediatly，使用javax.swing.Timer?)都可
- Undo one previous move or multiple previous moves.
- 和Task 2一起思考，更加优秀的棋局保存方式会引导你完成这个bonus
- Support online mode in Local Area Network.
- 使用java.Socket, 创建一个服务器，使用多线程来接受和处理客户端们的请求，客户端之间通过服务器通信？
- Pack the game as an executable that can be executed on a computer without JRE.
- 有一个软件叫exe4j...
- Design Human vs. AI mode of different difficulty levels, and make the AI player smarter.
- 思考AI应该如何加入战局，考虑多种AI策略：随机，贪心，α-β剪枝深度优先搜索？
- 其他bonus：回合计时器，棋子运行动画，GitHub项目合，剧情&拓展游戏机制？

### Some Words from the Author

这次project难度适中，针对得分点发力，尽量多做一些功能，就能拿到较高的分数，推荐各位同学们尽快开始，争取15周答辩好并去理论课答辩！
