/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 18/01/2020
 */
create database if not exists tabela;
use tabela;
create table  if  not exists salarioMinimo(
idsalarioMinimo int primary key auto_increment ,
dataVigencia date not null,
valordoMinimo double(8,2) not null,
decretoLei varchar(200) not null
);
insert into salarioMinimo(dataVigencia,valordoMinimo,decretoLei) 
values ("2020-01-01",1039.00,"Medida Provisória 916/2019"),
("2019-01-01",998.00,"Decreto 9.661/2019"),
("2018-01-01",954.00,"Decreto 9.255/2017"),
("2017-01-01",937.00,"Decreto 8.948/2016"),
("2016-01-01",880.00,"Decreto 8.618/2015"),
("2015-01-01",788.00,"Decreto 8.381/2014"),
("2014-01-01",724.00,"Decreto 8.166/2013"),
("2013-01-01",678.00,"Decreto 7.872/2012"),
("2012-01-01",622.00,"Decreto 7.655/2011"),
("2011-03-01",545.00,"Lei 12.382/2011"),
("2011-01-01",540.00,"MP 516/2010"),
("2010-01-01",510.00,"Lei 12.255/2010"),
("2009-02-01",465.00,"Lei 11.944/2009"),
("2008-03-01",415.00,"Lei 11.709/2008"),
("2007-04-01",380.00,"Lei 11.498/2007"),
("2006-04-01",350.00,"MP 288/2006"),
("2005-05-01",300.00,"Lei 11.164/2005"),
("2004-05-01",260.00,"MP 182/2004"),
("2003-04-01",240.00,"MP 116/2003"),
("2002-04-01",200.00,"MP 35/2002"),
("2001-04-01",180.00,"MP 2.142/2001 (atual 2.194-5)"),
("2000-04-03",151.00,"Lei 9.971/2000");