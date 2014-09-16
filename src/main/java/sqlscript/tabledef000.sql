create database if not exists invest001 default character set utf8 default collate utf8_general_ci;
use invest001;
set foreign_key_checks = 0;
SET @tables = '';
SELECT GROUP_CONCAT(table_schema, '.', table_name) INTO @tables
  FROM information_schema.tables 
  WHERE table_schema = 'invest001';
--    and not(table_name = 'zzdummy'); -- specify DB name here.
SET @tables = CONCAT('DROP TABLE if exists ', @tables);
--  select @tables;
PREPARE stmt FROM @tables;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
set foreign_key_checks = 1;

create table trackname(trackname varchar(20), provider varchar(30), primary key (trackname));
create table country(country varchar(5), primary key (country));
create table address(address int, country varchar(5), city varchar(30), street1 varchar(50), street2 varchar(50),
      zip varchar(10), primary key (address));
create table market(market varchar(20), address int, 
                primary key (market), foreign key (address) references address(address));
create table marketworktime(market varchar(20), validfrom char(8), timezone varchar(30), 
                validto char(8), dststart char(4), dstend char(4), opentime char(14), closetime char(14), 
                   primary key (market, validfrom));
create table symbol(market varchar(20), symbol varchar(20), address int, comment varchar(50), ipoyear int,  
                     primary key (market, symbol), foreign key (market) references market(market),
                     foreign key (address) references address(address) );
create table tracknameprod(trackname varchar(20),market varchar(20), symbol varchar(20), primary key(trackname, market, symbol), 
             foreign key (trackname) references trackname(trackname), foreign key(market, symbol) references symbol(market, symbol));
-- How many shares today. Can be updated by batch
create table totalshares(market varchar(20), symbol varchar(20), shares bigint, primary key (market, symbol));
create table symbolrelation(id int, market varchar(20), symbol varchar(20), ishome smallint, primary key (id), foreign key (market, symbol) references symbol(market, symbol));
-- Historical prices raw, tdate: transaction date
create table h0prices(market varchar(20), symbol varchar(20), tdate date, price0 DOUBLE, price1 double, priceh double, pricel double, quantity bigint, 
                primary key (market, symbol, tdate));
-- Historical prices adjusted according split, tdate: transaction date
create table h1prices(market varchar(20), symbol varchar(20), tdate date, price0 DOUBLE, price1 double, priceh double, pricel double, quantity bigint, 
                primary key (market, symbol, tdate));
-- Historical totoal number of shares, tdate: transaction date
create table h0shares(market varchar(20), symbol varchar(20), tdate date, shares bigint, split double);
create table h0dividend(market varchar(20), symbol varchar(20), tdate date, shares int,  dividend double);
create table h0report(market varchar(20), symbol varchar(20), begindate date, enddate date, begincash double, endcash double, cashflow double, beginasset double, endasset double,
                        assetchange double, profit double);
create table anamethod(anamethod varchar(20), anaclass varchar(20), primary key (anamethod));
create table anaresult(market varchar(20), symbol varchar(20), anamethod varchar(20), anadate date, resultid smallint, 
-- Normally only hchange is used for end price.
               hchangep1 double, 
               hchangep2 double,
               hchangep3 double,
               hchangep4 double,
               hchangep5 double,
-- Positive than 5 percents
               hchangep6 double,
               highestp double,
               hchangen1 double, 
               hchangen2 double,
               hchangen3 double,
               hchangen4 double,
               hchangen5 double,
-- Negative than 5 percents
               hchangen6 double,
               highestn double,
               hchange0 double,

               lchangep1 double, 
               lchangep2 double,
               lchangep3 double,
               lchangep4 double,
-- Positive than 5 percents
               lchangep5 double,
               lchangep6 double,
               lowestp double,
               lchangen1 double, 
               lchangen2 double,
               lchangen3 double,
               lchangen4 double,
               lchangen5 double,
-- Negative than 5 percents
               lchangen6 double,
               lowestn double,
               lchange0 double
               );
create table masterdownloading(taskid int, taskname varchar(20), taskurl varchar(200), batchno int, primary key (taskid));
create table task(taskdate char(8), taskid int, market varchar(20), symbol varchar(20), comment varchar(30), 
            status int, batchno int, createtime bigint, begintime  bigint, endtime bigint, primary key (taskdate, taskid),
            foreign key (market, symbol) references symbol(market, symbol));

create table properties(pname varchar(30), pvalue varchar(50), primary key (pname));
insert into masterdownloading (taskid, taskname, taskurl, batchno)
    values (1, 'nasdaqMaster', 'http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=NASDAQ&render=download', 1),
           (2, 'nyseMaster', 'http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=NYSE&render=download', 1),
           (3, 'amexMaster', 'http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=AMEX&render=download', 1);
create table masterbatch(batchno int,  text200 varchar(200),  primary key (batchno));
create table masterprogram(program varchar(20), batchno int, firstday char(8), primary key (program, batchno));