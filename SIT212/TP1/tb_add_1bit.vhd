LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.numeric_std.ALL;
LIBRARY UNISIM;
USE UNISIM.Vcomponents.ALL;
ENTITY add_1bit_add_1bit_sch_tb IS
END add_1bit_add_1bit_sch_tb;
ARCHITECTURE behavioral OF add_1bit_add_1bit_sch_tb IS 

  COMPONENT add_1bit
    PORT( Ak	:	IN	STD_LOGIC; 
          Bk	:	IN	STD_LOGIC; 
          Ck_1	:	IN	STD_LOGIC; 
          Sk	:	OUT	STD_LOGIC; 
          Ck	:	OUT	STD_LOGIC);
  END COMPONENT;

  SIGNAL Ak	:	STD_LOGIC;
  SIGNAL Bk	:	STD_LOGIC;
  SIGNAL Ck_1	:	STD_LOGIC;
  SIGNAL Sk	:	STD_LOGIC;
  SIGNAL Ck	:	STD_LOGIC;

BEGIN

  UUT: add_1bit PORT MAP(
    Ak => Ak, 
    Bk => Bk, 
    Ck_1 => Ck_1, 
    Sk => Sk, 
    Ck => Ck
    );

  Ak   <= '0', '0' after 20ns, '0' after 40ns, '0' after 60ns, '1' after 80ns, '1' after 100ns, '1' after 120ns, '1' after 140ns;     
  Bk   <= '0', '0' after 20ns, '1' after 40ns, '1' after 60ns, '0' after 80ns, '0' after 100ns, '1' after 120ns, '1' after 140ns;
  Ck_1 <= '0', '1' after 20ns, '0' after 40ns, '1' after 60ns, '0' after 80ns, '1' after 100ns, '0' after 120ns, '1' after 140ns;

END;
