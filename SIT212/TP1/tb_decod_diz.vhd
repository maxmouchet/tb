LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.numeric_std.ALL;
LIBRARY UNISIM;
USE UNISIM.Vcomponents.ALL;
ENTITY decod_diz_decod_diz_sch_tb IS
END decod_diz_decod_diz_sch_tb;
ARCHITECTURE behavioral OF decod_diz_decod_diz_sch_tb IS 

  COMPONENT decod_diz
    PORT( S_diz	:	OUT	STD_LOGIC_VECTOR (6 DOWNTO 0); 
          E	:	IN	STD_LOGIC_VECTOR (3 DOWNTO 0));
  END COMPONENT;

  SIGNAL S_diz	:	STD_LOGIC_VECTOR (6 DOWNTO 0);
  SIGNAL E	:	STD_LOGIC_VECTOR (3 DOWNTO 0);

BEGIN

  UUT: decod_diz PORT MAP(
    S_diz => S_diz, 
    E => E
    );

  E <= "0000", "1010" after 20ns;

END;
