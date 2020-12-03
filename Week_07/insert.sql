 delimiter $$ 
 drop procedure if exists insert_user; 
 create procedure insert_user(in max_num int(10)) begin declare i int default 0; 
 set autocommit=0; 
 repeat set i=i+1; 
 insert into user (username,password,phone,email) values(i,'1','1','1'); 
 until i=max_num end repeat; 
 commit; 
 end $$

call insert_dept(1000000);