library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity decodeur_uni is
  Port ( E : in std_logic_vector(3 downto 0);
         Suni : out std_logic_vector(6 downto 0));
end decodeur_uni;

architecture Behavioral of decodeur_uni is

begin

  P1 : process (E)

  begin
    case E is
      when "0000" => Suni <= "0000001";
      when "0001" => Suni <= "1001111";
      when "0010" => Suni <= "0010010";
      when "0011" => Suni <= "0000110";
      when "0100" => Suni <= "1001100";
      when "0101" => Suni <= "0100100";
      when "0110" => Suni <= "0100000";
      when "0111" => Suni <= "0001111";
      when "1000" => Suni <= "0000000";
      when "1001" => Suni <= "0000100";
      when "1010" => Suni <= "0000001";
      when "1011" => Suni <= "1001111";
      when "1100" => Suni <= "0010010";
      when "1101" => Suni <= "0000110";
      when "1110" => Suni <= "1001100";
      when "1111" => Suni <= "0100100";
      when others => Suni <= "0000000";

    end case;

  end process;



end Behavioral;
