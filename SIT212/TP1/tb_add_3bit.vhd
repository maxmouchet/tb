LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.numeric_std.ALL;
LIBRARY UNISIM;
USE UNISIM.Vcomponents.ALL;
ENTITY add_3bit_add_3bit_sch_tb IS
END add_3bit_add_3bit_sch_tb;
ARCHITECTURE behavioral OF add_3bit_add_3bit_sch_tb IS 

  COMPONENT add_3bit
    PORT( A	:	IN	STD_LOGIC_VECTOR (2 DOWNTO 0); 
          B	:	IN	STD_LOGIC_VECTOR (2 DOWNTO 0); 
          S	:	OUT	STD_LOGIC_VECTOR (3 DOWNTO 0));
  END COMPONENT;

  SIGNAL A	:	STD_LOGIC_VECTOR (2 DOWNTO 0);
  SIGNAL B	:	STD_LOGIC_VECTOR (2 DOWNTO 0);
  SIGNAL S	:	STD_LOGIC_VECTOR (3 DOWNTO 0);

BEGIN

  UUT: add_3bit PORT MAP(
    A => A, 
    B => B, 
    S => S
    );

  A <= "000", "111" after 20ns;
  B <= "111";

END;
