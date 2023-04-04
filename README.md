<img src="https://user-images.githubusercontent.com/88530565/229351696-6728a93e-862c-4e61-8dfb-1fb87b98aaca.png" width="500">

# 2023 Solution Challenge: Emotional Diary

2023 GDSC Kookmin - Team 2
<br>
<span style="color:gray"> Team members :
<br>
    Dongwon Kim ([@EastWon0103](https://github.com/EastWon0103))
<br> 
    Heegun Kwak ([@Vargun](https://github.com/vargun/))
<br> 
    Gwangyeol Yu ([@yugwangyeol](https://github.com/yugwangyeol))
<br> 
    Yujin Kim ([@yuujinkim](https://github.com/yuujinkim))
</span>


## Project Introduction

### <strong> Problem situation </strong>

Depression is a serious social problem in Korea. According to the results of statistical analysis of depression and anxiety disorders released by the Health Insurance Review and Assessment Service(HIRA), a national medical evaluation agency in Korea, the number of depression patients has increased by 35.1% over the past five years (2017-2022).([HIRA](https://www.hira.or.kr/bbsDummy.do?pgmid=HIRAA020041000100&brdScnBltNo=4&brdBltNo=10627&pageIndex=1))

As such, many people have difficulty managing their emotions due to their busy daily lives and stress. This not only suppresses or ignores your emotions, but can also cause problems in your interpersonal relationships.

### <strong> Goals </strong>

To address these issues, we propose the EmotionalDiary.
Our chosen UN sustainable development goal is Goal 3: GOOD HEALTH AND WELL-BEING.
<br>
##### Goal 3. GOOD HEALTH AND WELL-BEING
<img src="https://user-images.githubusercontent.com/88530565/229352121-7b862cc6-aa6c-49ab-89c2-06712311bb31.jpg" width="300px">

Emotional Diary is a diary that allows you to record your daily emotions and express your feelings. It also includes a TODO feature that can help you plan your routines.
Emotional Diary analyzes your diary and recommends songs and movies that suit your situation. In addition, it analyzes the emotions of the written diary and proposes TODO to resolve negative emotions if they are recognized.
<br>
HIRA recommended having adequate conversations with people and engaging in physical activities such as light walking and jogging to prevent depression.
Based on this, we decided to recommend TODO to users.


## Detail of function

### <strong> 1. Sign up </strong>
<img src="https://user-images.githubusercontent.com/88530565/229343543-efe5fd49-4fef-42e0-80db-025cfd96f2bb.gif" width="300">
<br>
<ul>
    <li> Used the Google Social Login feature and retrieve the user's data from the database using their email information.
    <li> If there is no existing user information in the database, new user information will be automatically registered.
    <li> Automatically log in, saving the information of the most recently logged in user.
</ul>

### <strong> 2. Write diary </strong>
<img src="https://user-images.githubusercontent.com/88530565/229343748-bf23c260-272a-40eb-ab8b-0c8504b43a12.gif" width="300">
<br>
<ul>
    <li> The user writes the day's diary and emotion score.
    <li> Can choose a date and view the diary entry written on that day. 
    <li> The recommendation system generates a recommendation list based on the inputted diary data.
</ul>

### <strong> 3. Add TODO </strong>
<img src="https://user-images.githubusercontent.com/88530565/229343820-d0eeeb24-b510-428d-abef-1811e0539f01.gif" width="300">
<br>
<ul>
    <li> Also, users can select a date to check or add TODO items for that day.
    <li> Each button has a function to change the completion status of an item and modify or delete it.
</ul>

### <strong> 4. Recommended music and movie - based on user's diary </strong>

<img src="https://user-images.githubusercontent.com/88530565/229343918-24b5b67f-00fd-4cce-a4b4-8f07a08a3f67.gif"
width="300">
<br>
<ul>
    <li> Once the recommendation system is completed, it will display recommended music, movies, and tasks (TODOs) to the user.
    <li> If user clicks on a recommended movie or music item, search for information on the website.
    <li> Can check other recommended movies and music items by pressing the Refresh button.
</ul>

## Tech Stack

### Back End
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/google cloud-4285F4?style=for-the-badge&logo=google cloud&logoColor=white"> 


### Front End
<img src="https://img.shields.io/badge/android-3DDC84?style=for-the-badge&logo=android&logoColor=white"> <img src="https://img.shields.io/badge/firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white"> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 

### AI
<img src="https://img.shields.io/badge/PyTorch-EE4C2C?style=for-the-badge&logo=PyTorch&logoColor=white"> <img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=Python&logoColor=white">
