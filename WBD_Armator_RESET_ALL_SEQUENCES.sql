-- Procedure to reset sequence with the proper name.
create or replace
procedure reset_seq( p_seq_name in varchar2 )
is
    l_val number;
begin
    execute immediate
    'select ' || p_seq_name || '.nextval from dual' INTO l_val;

    execute immediate
    'alter sequence ' || p_seq_name || ' increment by -' || l_val || 
                                                          ' minvalue 0';

    execute immediate
    'select ' || p_seq_name || '.nextval from dual' INTO l_val;

    execute immediate
    'alter sequence ' || p_seq_name || ' increment by 1 minvalue 0';
end;
/

EXECUTE reset_seq('adresyseq');
EXECUTE reset_seq('atrakcjeseq');
EXECUTE reset_seq('biuroseq');
EXECUTE reset_seq('czarteryseq');
EXECUTE reset_seq('klienciseq');
EXECUTE reset_seq('kursyseq');
EXECUTE reset_seq('lodzieseq');
EXECUTE reset_seq('modelelodziseq');
EXECUTE reset_seq('pocztyseq');
EXECUTE reset_seq('portyseq');
EXECUTE reset_seq('pracownicyseq');
EXECUTE reset_seq('rejsyseq');
EXECUTE reset_seq('wlascicieleseq');