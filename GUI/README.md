# Final Project: C212 Spring 2019
#### Group Members: Camisa Vines, Rebecca Myers, Evan Watson, John Ruszkowski
#### Date Last Modified: 4/26/19


<br/>

## Movie Theater Java Application

Things to know:
This is an Eclipse project. So all of the java source code can be found in the folder named **src**.
* This Movie Theater Application has three main perspectives, **Customer View, Payment View, and Manager View**. Each of which are explained thoroughly in description.pdf.
  * Briefly, the application serves as a way to view upcoming movies, purchase tickets, and in "Manager Mode", edit any movie deatils.
  
<br/>

## System Features 
The features listed are required of us and have been or will be implemented.
```
public class MainWindow {} //Creates all movie object; and serves as the main function to entire application that holds universal methods. 
public class Payment {} //Takes the type of payment (cash or credit), billing infomation, updates the Theater class. Holds special deals (methods). 
public class Ticket {} //Sets ticket price; and sets the new ticket price based upon the deal.
public class ShoppingCart {} //Stores user's tickets; prints receipt.
public class Manager {} //This portion of the project is password protected, but once in this mode, the user can add, and edit movie details. 
public class Movie {} //Updates movies (edit and add), and updates movie database as well. Sorts movies by genre and rating.  Holds panels for dropdowns for number of tickets (connected to ShoppingCart), dropdowns for showtimes, and buttons for each movie (movie selection, add, edit).  
```
<br/>
 
## Files/Databases 
.txt files can be found in the repository.  movies.txt holds the data for all movies available for purchase.  Fields include: title, genre, year, and rating. Additionally there is another .txt file holding editable movie data. Fields include: title, genre, ticket price, runtime, rating, and showtimes.



<!--
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
 -->
