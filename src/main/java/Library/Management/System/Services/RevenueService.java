package Library.Management.System.Services;

import Library.Management.System.Entities.Transaction;
import Library.Management.System.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueService {
    @Autowired
    private TransactionRepository transactionRepository;

    public String totalRevenue(){
        int total=0;
        List<Transaction> transactionList = transactionRepository.findAll();
        for (Transaction transaction : transactionList){
            total+=transaction.getFineAmount();
        }
        return "Total revenue : " + total;
    }
}
